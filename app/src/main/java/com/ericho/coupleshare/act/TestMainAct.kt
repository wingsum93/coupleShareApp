package com.ericho.coupleshare.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import butterknife.bindView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.TestMainRecyclerAdapter
import com.ericho.coupleshare.setting.model.AppTestBo
import java.util.ArrayList

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestMainAct: AppCompatActivity() {

    val recyclerView: RecyclerView by bindView<RecyclerView>(R.id.recyclerView)
    var testMainRecyclerAdapter: TestMainRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_test_main)
        init()
    }

    private fun init() {
        //list
        val layoutManager = LinearLayoutManager(application)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val list = getMainList()
        testMainRecyclerAdapter = TestMainRecyclerAdapter(this, list)
        recyclerView.adapter = testMainRecyclerAdapter
        testMainRecyclerAdapter?.onItemClickListener = object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val appTestBo: AppTestBo = list[position]
                startCorrespondAct(appTestBo.code)
            }
        }
        //

    }



    private fun getMainList(): List<AppTestBo> {
        val res = ArrayList<AppTestBo>()
        //        res.add(AppTestBo.create(TestQuerySCCAct.CODE, "SCC", "search scc"));
        res.add(AppTestBo.create(TestSelectPhotoAct.hint, "TE", "search Photo"))
        res.add(AppTestBo.create(TestAppBarAct.FLAG, "TE", "AppBar"))
        res.add(AppTestBo.create(TestBiliBiliAppBarAct.FLAG, "TE", "Bili AppBar"))
        res.add(AppTestBo.create(TestCollapseToolbarLayoutAndTabLayoutAct.FLAG, "TE", "Bar vs TabLayout"))
        return res
    }

    private fun startCorrespondAct(internalCode: String?) {
        when (internalCode) {
            TestSelectPhotoAct.hint -> startActivity(Intent(this, TestSelectPhotoAct::class.java))
            TestAppBarAct.FLAG -> startActivity(Intent(this, TestAppBarAct::class.java))
            TestBiliBiliAppBarAct.FLAG -> startActivity(Intent(this, TestBiliBiliAppBarAct::class.java))
            TestCollapseToolbarLayoutAndTabLayoutAct.FLAG -> startActivity(Intent(this, TestCollapseToolbarLayoutAndTabLayoutAct::class.java))
        }
    }

    companion object {
        private val tag = "TestMainAct"
        @JvmStatic
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, TestMainAct::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }
}