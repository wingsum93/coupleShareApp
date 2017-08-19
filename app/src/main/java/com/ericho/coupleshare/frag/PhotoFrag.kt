package com.ericho.coupleshare.frag

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ProgressBar
import android.widget.TextView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.PhotoAddAct
import com.ericho.coupleshare.act.ViewPhotoAct
import com.ericho.coupleshare.adapter.PhotoAdapter
import com.ericho.coupleshare.eventbus.PhotoUploadEvent
import com.ericho.coupleshare.network.PhotoHttpLoader
import com.ericho.coupleshare.network.Result
import com.ericho.coupleshare.mvp.PhotoBo
import com.ericho.coupleshare.mvp.PhotosContract
import com.ericho.coupleshare.mvp.presenter.PhotoPresenter
import com.ericho.coupleshare.util.ZoomImageHelper
import com.ericho.coupleshare.util.safe
import com.headerfooter.songhang.library.SmartRecyclerAdapter
import kotlinx.android.synthetic.main.act_status_notice_add.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import timber.log.Timber

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class PhotoFrag:BaseFrag(), PhotosContract.View, SwipeRefreshLayout.OnRefreshListener,LoaderManager.LoaderCallbacks<Result<PhotoBo>>{

    var recyclerView: RecyclerView? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var fab :FloatingActionButton? = null

    private var adapter: PhotoAdapter? = null
    private var smartAdapter: SmartRecyclerAdapter? = null
    private var layoutManager:RecyclerView.LayoutManager? = null
    private var mPresenter: PhotosContract.Presenter? = null
    private var zoomImageHelper :ZoomImageHelper? = null

    private var list: ArrayList<PhotoBo> = ArrayList<PhotoBo>()
    var mFirstCreate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_photo, container, false)
        EventBus.getDefault().register(this)
        Timber.d("onCreateView")

        return view
    }

    private fun init() {

        recyclerView = view!!.findViewById(R.id.recyclerView) as RecyclerView
        swipeRefreshLayout = view?.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        fab = view?.findViewById(R.id.fab) as FloatingActionButton
        //listener
        layoutManager = GridLayoutManager(activity, 3)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        if(adapter == null) {
            adapter = PhotoAdapter(activity, list)
            adapter!!.setOnItemClickListener(AdapterView.OnItemClickListener { _, view, position, id ->
//                zoomImageHelper?.zoomImageFromThumb(view,urlPath = list[position].fullPath)
                val uriList = list.map {
                    it.fullPath
                }.toTypedArray()

                val actOpt =
                ActivityOptions.makeScaleUpAnimation(view,view.x.toInt(),view.y.toInt(),view.width,view.height)
                startActivityForResult(ViewPhotoAct.newIntent(activity,uriList,position),REQ_VIEW_PHOTO,actOpt.toBundle())
            })
        }
        if(smartAdapter == null) {
            smartAdapter = SmartRecyclerAdapter(adapter!!)
        }
        recyclerView!!.adapter = smartAdapter
        swipeRefreshLayout?.setOnRefreshListener(this)

        mPresenter = PhotoPresenter(this)
        mPresenter?.start()

        loaderManager.initLoader(LOADER_ID,null,this)

        zoomImageHelper = ZoomImageHelper.Builder(activity)
                .setRootId(R.id.frameLayout)
                .setExpendViewId(R.id.photo_full_image)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
                .setFragmentMode(true)
                .setFragmentFindViewFunction {
                    view
                }
                .build()
        fab?.setOnClickListener {
            v->
            //b = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(),
            //                                         view.getHeight()).toBundle();
            val bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.parseColor("#308cf8"));

            val b = ActivityOptions.makeScaleUpAnimation(v, v.x.toInt(), v.y.toInt(), v.width,v.height).toBundle();

            startActivity(Intent(activity, PhotoAddAct::class.java),b)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.add,menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_add -> {
                if (activity != null) {
                    startActivity(Intent(activity, PhotoAddAct::class.java))
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        init()

    }

    override fun onRefresh() {
        swipeRefreshLayout?.isRefreshing = false

        loaderManager.restartLoader(LOADER_ID,null,this)
    }

    override fun setPresenter(presenter: PhotosContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showAddPhotoUI() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQ_VIEW_PHOTO->{
                val pos = data?.getIntExtra("RESULT",0)
                if(pos!=null) layoutManager?.scrollToPosition(pos)
            }
            else->super.onActivityResult(requestCode, resultCode, data)
        }
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
        val progressBar = v.findViewById(R.id.progressBar) as ProgressBar

        val t = getString(R.string.uploading) + "$currentCount / $totalCount"
        textView.text = t

        smartAdapter?.setHeaderView(v)
    }

    override fun showPhotoList(photos: List<PhotoBo>) {
        Timber.d("showPhotoList")
        adapter?.update(photos)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Result<PhotoBo>> {
        val load = PhotoHttpLoader.from(activity,args)
        return load
    }

    override fun onLoadFinished(loader: Loader<Result<PhotoBo>>?, data: Result<PhotoBo>?) {
        if(data!!.success()){
            list.clear()
            list.addAll(data.result.safe())
            Timber.d("netowrk get data ok! ${smartAdapter}")
            adapter!!.notifyDataSetChanged()
            smartAdapter!!.notifyDataSetChanged()
        }else{
            val ioException = data.error
            Timber.w(ioException)
            activity.toast("load photo list fail ${ioException?.message}")
        }
    }

    override fun onLoaderReset(loader: Loader<Result<PhotoBo>>?) {
        list.clear()
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this)
        Timber.d("onDestroyView")
        mFirstCreate = false
        zoomImageHelper?.onDestoryView()
        zoomImageHelper = null
        recyclerView?.adapter = null
        adapter = null
        smartAdapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        loaderManager.destroyLoader(LOADER_ID)
        Timber.d("onDestroy")
        super.onDestroy()
    }

    companion object {

        val LOADER_ID = 111
        val REQ_VIEW_PHOTO = 888
        @JvmStatic
        fun newInstance(): PhotoFrag {
            val frag = PhotoFrag()
            val bundle = Bundle()
            frag.arguments = bundle
            return frag
        }
    }
}