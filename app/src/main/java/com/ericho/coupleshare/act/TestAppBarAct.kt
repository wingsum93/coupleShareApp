package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.ButterKnife
import com.ericho.coupleshare.R
import kotlinx.android.synthetic.main.act_test_app_bar.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestAppBarAct : AppCompatActivity() {

    val FLAG = "TestAppBarAct"

    var collapsingToolbarLayout: CollapsingToolbarLayout? = toolbar_layout
    var floatingActionButton: FloatingActionButton? = fab
    var textView: TextView? = text

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_app_bar)
        textView?.setText(generateLargeText())
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
        val FLAG = "TestAppBarAct"
    }
}