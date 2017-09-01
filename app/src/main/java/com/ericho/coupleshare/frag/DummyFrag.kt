package com.ericho.coupleshare.frag

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.ericho.coupleshare.R

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class DummyFrag :BaseFrag(){
    val textView: TextView by bindView(R.id.text1)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val linearLayout: LinearLayout by bindView(R.id.base)



    companion object {
        private val K_COLOR = "K_COLOR"
        @JvmStatic
        @JvmOverloads
        fun newInstance(@ColorRes color: Int = Color.BLACK): Fragment {
            val frag = DummyFrag()
            val bundle = Bundle()
            bundle.putInt(K_COLOR, color)
            frag.arguments = bundle
            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_dummy, container, false)
        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val bundle = arguments
        val colorint = bundle.getInt(K_COLOR, android.R.color.holo_green_dark)
        textView.setBackgroundColor(colorint)
        linearLayout.setBackgroundColor(colorint)

        textView.text = "asd211dsdsdasdasdas21d3a1d3"
    }
}