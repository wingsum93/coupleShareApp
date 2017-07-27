package com.ericho.coupleshare.frag

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import butterknife.bindView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.PhotoAddAct
import com.ericho.coupleshare.act.TestHoldAct
import com.ericho.coupleshare.adapter.PhotoAdapter
import com.ericho.coupleshare.eventbus.PhotoUploadEvent
import com.ericho.coupleshare.http.model.BaseResponse
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.mvp.PhotosContract
import com.ericho.coupleshare.mvp.presenter.PhotoPresenter
import com.ericho.coupleshare.util.AHttpHelperB
import com.ericho.coupleshare.util.NetworkUtil
import com.ericho.coupleshare.util.ZoomImageHelper
import com.ericho.coupleshare.util.safe
import com.ericho.coupleshare.util.showToastMessage
import com.google.gson.reflect.TypeToken
import com.headerfooter.songhang.library.SmartRecyclerAdapter
import kotlinx.android.synthetic.main.act_status_notice_add.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import kotlin.collections.ArrayList

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class PhotoFrag:BaseFrag(), PhotosContract.View, FabListener,SwipeRefreshLayout.OnRefreshListener{

    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.recyclerView)
    val swipeRefreshLayout: SwipeRefreshLayout by bindView<SwipeRefreshLayout>(R.id.swipeRefreshLayout)


    private var adapter: PhotoAdapter? = null
    private var smartAdapter: SmartRecyclerAdapter? = null

    private lateinit var mPresenter: PhotosContract.Presenter
    lateinit var httpHelper :AHttpHelperB<BaseResponse<PhotoBo>>
    lateinit var zoomImageHelper :ZoomImageHelper

    private var list: ArrayList<PhotoBo> = ArrayList<PhotoBo>()
    var mFirstCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")


        httpHelper = AHttpHelperB.Builder<BaseResponse<PhotoBo>>()
                .setFail { call, ioException ->
                    Timber.w(ioException)
                    showToastMessage("load photo list fail ${ioException.message}")
                }.setTransformMethod { App.gson.fromJson(it,object :TypeToken<BaseResponse<PhotoBo>>(){}.type) }
                .setSuccessMethod { call, response ->
                    list.clear()
                    list.addAll(response.extra.safe())
                    Timber.d("netowrk get data ok! ${smartAdapter}")
                    Timber.d("recycler's dapter = ${recyclerView.adapter}")
                    adapter!!.notifyDataSetChanged()
                }
                .build()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_photo, container, false)
        EventBus.getDefault().register(this)
        Timber.d("onCreateView")



        return view
    }

    private fun init() {


        //listener
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        if(adapter == null) {
            adapter = PhotoAdapter(activity, list)
            adapter!!.setOnItemClickListener(AdapterView.OnItemClickListener { _, view, position, id ->
                zoomImageHelper.zoomImageFromThumb(view,urlPath = list[position].fullPath)
            })
        }
        if(smartAdapter == null) {
            smartAdapter = SmartRecyclerAdapter(adapter!!)
        }
        recyclerView.adapter = smartAdapter
        smartAdapter!!.notifyDataSetChanged()
        swipeRefreshLayout.setOnRefreshListener(this)

        mPresenter = PhotoPresenter(this)
        mPresenter.start()

        loadPhotoList()

        zoomImageHelper = ZoomImageHelper.Builder(activity)
                .setRootId(R.id.frameLayout)
                .setExpendViewId(R.id.expanded_image)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .build()
    }

    private fun loadPhotoList() {

        httpHelper.run(NetworkUtil.photo_get())
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        init()

    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        loadPhotoList()
    }

    override fun setPresenter(presenter: PhotosContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showAddPhotoUI() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSubscribe(event:PhotoUploadEvent){
        Timber.d("onSubscribe ${App.gson.toJson(event)}")
        when{
            !event.interrupt->{
                val b = event.totalCount != event.currentCount
                if(b){
                    showHeaderView(event.totalCount,event.currentCount)
                }else{
                    removeHeaderView()
                }
            }
            event.interrupt ->{}
        }
    }

    private fun removeHeaderView() {
        smartAdapter?.removeHeaderView()
    }

    private fun showHeaderView(totalCount: Int, currentCount: Int) {
        val v = LayoutInflater.from(activity).inflate(R.layout.header_upload_image,container,false)

        val textView = v.findViewById(R.id.txv_progress_state) as TextView
        val progressBar = v.findViewById(R.id.progressBar) as ContentLoadingProgressBar

        textView.text = getString(R.string.uploading)

        smartAdapter?.setHeaderView(v)
    }

    override fun showPhotoList(photos: List<PhotoBo>) {
        Timber.d("showPhotoList")
        adapter?.update(photos)
    }

    override fun onAttachFloatingActionListener(floatingActionButton: FloatingActionButton) {
        floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(App.context!!.resources, R.drawable.ic_add_white_24dp, null))
        floatingActionButton.setOnClickListener {
            v -> Timber.d("fab photo click")
            if (activity != null) {
                startActivity(Intent(activity, PhotoAddAct::class.java))
            }
        }
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        Timber.d("onDestroyView")
        mFirstCreate = false
        zoomImageHelper.onDestoryView()
        recyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(): PhotoFrag {
            val frag = PhotoFrag()
            val bundle = Bundle()
            frag.arguments = bundle
            return frag
        }
    }
}