package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.bindView
import com.ericho.coupleshare.R
import org.jetbrains.anko.toast

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestAppBarAct : AppCompatActivity() {

    val FLAG = "TestAppBarAct"

    val collapsingToolbarLayout: CollapsingToolbarLayout by bindView<CollapsingToolbarLayout>(R.id.toolbar_layout)
    val floatingActionButton: FloatingActionButton by bindView<FloatingActionButton>(R.id.fab)
    val textView: TextView by bindView<TextView>(R.id.text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_app_bar)
        init()
    }

    fun init(){
        textView.text = generateLargeText()

        floatingActionButton.setOnClickListener({
            _-> toast("fab click!")
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
        val FLAG = "TestAppBarAct"
    }
}