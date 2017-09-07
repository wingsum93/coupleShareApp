package com.ericho.coupleshare.frag

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.StatusAddAct_copy
import com.ericho.coupleshare.adapter.StatusAdapter
import com.ericho.coupleshare.eventbus.StatusEvent
import com.ericho.coupleshare.network.model.BaseResponse
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.StatusBo
import com.ericho.coupleshare.mvp.StatusContract
import com.ericho.coupleshare.util.AHttpHelperB
import com.ericho.coupleshare.util.NetworkUtil
import com.ericho.coupleshare.util.ZoomImageHelper
import com.ericho.coupleshare.util.safe
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class StatusFrag:BaseFrag(), StatusContract.View, FabListener {
    var recyclerView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var fab :FloatingActionButton? = null

    private var adapter: StatusAdapter? = null

    private var presenter: StatusContract.Presenter? = null
    private var httpHelper: AHttpHelperB<BaseResponse<StatusBo>>? = null
    private var list: ArrayList<StatusBo> = ArrayList()
    private var zoomImageHelper:ZoomImageHelper? = null


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

        fab = view?.findViewById(R.id.fab)
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view?.findViewById(R.id.recyclerView)


        //listener
        adapter = StatusAdapter(activity, list)
        adapter!!.imageClickListener = AdapterView.OnItemClickListener {
            _, view, position, id ->
            zoomImageHelper?.zoomImageFromThumb(view, (list[position].fullPath))
        }
        recyclerView?.setLayoutManager(LinearLayoutManager(activity))
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setAdapter(adapter)
        swipeRefreshLayout?.setOnRefreshListener {
            //
            activity.toast("refresh")
            swipeRefreshLayout?.isRefreshing = false
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
        fab?.setOnClickListener {
            startActivity(Intent(activity, StatusAddAct_copy::class.java))
        }
        loadStatusList()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_add -> {
                if (activity != null) {
                    startActivity(Intent(activity, StatusAddAct_copy::class.java))
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun loadStatusList(){

        val request: Request = NetworkUtil.status_get()
        httpHelper?.run(request)
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
        floatingActionButton.visibility = View.GONE
        floatingActionButton.setOnClickListener {
            if (activity != null) {
                startActivity(Intent(activity, StatusAddAct_copy::class.java))
            }
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        zoomImageHelper?.onDestoryView()
        zoomImageHelper = null
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