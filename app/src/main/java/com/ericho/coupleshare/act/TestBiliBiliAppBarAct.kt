package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.ButtonBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.ericho.coupleshare.CollapsingToolbarLayoutState
import com.ericho.coupleshare.R
import kotlinx.android.synthetic.main.act_test_bilibili_app_bar.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestBiliBiliAppBarAct:RxLifecycleAct() {



    internal var collapsingToolbarLayout: CollapsingToolbarLayout = toolbar_layout
    internal var floatingActionButton: FloatingActionButton = fab
    internal var textView: TextView = text



    private var state: CollapsingToolbarLayoutState? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_bilibili_app_bar)
        textView.setText(generateLargeText())
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED//修改状态标记为展开
                    collapsingToolbarLayout.setTitle("EXPANDED")//设置title为EXPANDED
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    collapsingToolbarLayout.setTitle("")//设置title不显示
                    playButton.setVisibility(View.VISIBLE)//隐藏播放按钮
                    state = CollapsingToolbarLayoutState.COLLAPSED//修改状态标记为折叠
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        playButton.setVisibility(View.GONE)//由折叠变为中间状态时隐藏播放按钮
                    }
                    collapsingToolbarLayout.setTitle("INTERNEDIATE")//设置title为INTERNEDIATE
                    state = CollapsingToolbarLayoutState.INTERNEDIATE//修改状态标记为中间
                }
            }
        })
    }

    private fun generateLargeText(): String {
        val size = 35555
        val builder = StringBuilder()
        for (i in 0..size - 1) {
            builder.append("a")
            if (i % 13 == 0)
                builder.append("\n")

        }
        return builder.toString()
    }

    companion object {
        @JvmField
        val FLAG = "TestBiliBiliAppBarAct"
    }
}