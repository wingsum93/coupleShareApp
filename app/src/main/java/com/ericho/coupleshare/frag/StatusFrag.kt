package com.ericho.coupleshare.frag

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import butterknife.bindView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.StatusAddAct_copy
import com.ericho.coupleshare.adapter.StatusAdapter
import com.ericho.coupleshare.eventbus.StatusEvent
import com.ericho.coupleshare.http.model.BaseResponse
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.StatusBo
import com.ericho.coupleshare.mvp.StatusContract
import com.ericho.coupleshare.util.AHttpHelperB
import com.ericho.coupleshare.util.NetworkUtil
import com.ericho.coupleshare.util.ZoomImageHelper
import com.ericho.coupleshare.util.safe
import com.ericho.coupleshare.util.showToastMessage
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import kotlin.collections.ArrayList

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class StatusFrag:BaseFrag(), StatusContract.View, FabListener {
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipeRefreshLayout)

    private var adapter: StatusAdapter? = null

    private var presenter: StatusContract.Presenter? = null
    lateinit var httpHelper: AHttpHelperB<BaseResponse<StatusBo>>
    private var list: ArrayList<StatusBo> = ArrayList()
    lateinit var zoomImageHelper:ZoomImageHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_status, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        EventBus.getDefault().register(this)
    }

    private fun init() {


        //listener
        adapter = StatusAdapter(activity, list)
        adapter!!.imageClickListener = AdapterView.OnItemClickListener {
            _, view, position, id ->
            zoomImageHelper.zoomImageFromThumb(view, (list[position].fullPath))
        }
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(adapter)
        swipeRefreshLayout.setOnRefreshListener {
            //
            showToastMessage("refresh")
            swipeRefreshLayout.isRefreshing = false
            loadStatusList()
        }

        httpHelper = AHttpHelperB.Builder<BaseResponse<StatusBo>>()
                .setFail { call, ioException ->
                    Timber.w(ioException)
                }
                .setTransformMethod { App.gson.fromJson(it,object :TypeToken<BaseResponse<StatusBo>>(){}.type) }
                .setSuccessMethod { call, baseResponse ->
                    Timber.d(App.gson.toJson(baseResponse.extra))
                    list.clear()
                    list.addAll(baseResponse.extra.safe())
                    adapter!!.notifyDataSetChanged()
                }
                .build()
        zoomImageHelper = ZoomImageHelper.Builder(activity)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .setExpendViewId(R.id.expanded_image)
                .setRootId(R.id.frameLayout)
                .build()
        loadStatusList()
    }

    fun loadStatusList(){

        val request: Request = NetworkUtil.status_get()
        httpHelper.run(request)
    }
    @Subscribe()
    fun onStatusUpdate(event:StatusEvent){
        if(activity==null) return
        loadStatusList()
    }
    override fun setPresenter(presenter: StatusContract.Presenter) {
        this.presenter = presenter
    }

    override fun onAttachFloatingActionListener(floatingActionButton: FloatingActionButton) {
        floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(App.context!!.resources, R.drawable.ic_add_white_24dp, null))
        floatingActionButton.setOnClickListener {
            if (activity != null) {
                startActivity(Intent(activity, StatusAddAct_copy::class.java))
            }
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        zoomImageHelper.onDestoryView()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(): StatusFrag {
            val frag = StatusFrag()
            val bundle = Bundle()
            frag.arguments = bundle
            return frag
        }
    }
}