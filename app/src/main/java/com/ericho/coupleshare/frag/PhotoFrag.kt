package com.ericho.coupleshare.frag

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.bindView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.UpdateStatusAct
import com.ericho.coupleshare.adapter.PhotoAdapter
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.Photo
import com.ericho.coupleshare.mvp.PhotosContract
import com.ericho.coupleshare.mvp.presenter.PhotoPresenter
import timber.log.Timber
import java.util.ArrayList

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class PhotoFrag:BaseFrag(), PhotosContract.View, FabListener {

    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.recyclerView)


    private var adapter: PhotoAdapter? = null

    private lateinit var mPresenter: PhotosContract.Presenter

    private var list: List<Photo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_photo, container, false)
        return view
    }

    private fun init() {


        //listener
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = adapter


        list = ArrayList<Photo>()
        adapter = PhotoAdapter(activity, list)

        mPresenter = PhotoPresenter(this)
        mPresenter.start()

    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }


    override fun setPresenter(presenter: PhotosContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showAddPhotoUI() {

    }

    override fun showPhotoList(photos: List<Photo>) {
        Timber.d("showPhotoList")
        adapter?.update(photos)
    }

    override fun onAttachFloatingActionListener(floatingActionButton: FloatingActionButton) {
        floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(App.context!!.resources, R.drawable.ic_add_white_24dp, null))
        floatingActionButton.setOnClickListener {
            v -> Timber.d("fab photo click")
            startActivity(Intent(activity,UpdateStatusAct::class.java))
        }
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