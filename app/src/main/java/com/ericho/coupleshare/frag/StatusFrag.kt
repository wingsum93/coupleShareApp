package com.ericho.coupleshare.frag

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.R
import com.ericho.coupleshare.act.StatusAddAct
import com.ericho.coupleshare.adapter.StatusAdapter
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.mvp.StatusBo
import com.ericho.coupleshare.mvp.StatusContract
import java.util.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class StatusFrag:BaseFrag(), StatusContract.View, FabListener {
    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.recyclerView)

    private var adapter: StatusAdapter? = null

    private var presenter: StatusContract.Presenter? = null

    private var list: List<StatusBo>? = null

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

    }

    private fun init() {


        //listener
        recyclerView.setLayoutManager(GridLayoutManager(activity, 3))
        recyclerView.setAdapter(adapter)

        list = ArrayList<StatusBo>()
        adapter = StatusAdapter(activity, list)

    }


    override fun setPresenter(presenter: StatusContract.Presenter) {
        this.presenter = presenter
    }

    override fun onAttachFloatingActionListener(floatingActionButton: FloatingActionButton) {
        floatingActionButton.setImageDrawable(ResourcesCompat.getDrawable(App.context!!.resources, R.drawable.ic_add_white_24dp, null))
        floatingActionButton.setOnClickListener {
            startActivity(Intent(activity, StatusAddAct::class.java))
        }
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