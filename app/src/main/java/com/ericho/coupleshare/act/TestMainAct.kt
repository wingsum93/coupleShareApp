package com.ericho.coupleshare.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.ButterKnife
import butterknife.bindView
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.TestMainRecyclerAdapter
import com.ericho.coupleshare.setting.model.AppTestBo
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class TestMainAct:RxLifecycleAct() {

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

        //
        testMainRecyclerAdapter?.getPositionClicks()
                ?.debounce(300, TimeUnit.MILLISECONDS)
                ?.compose(this.bindToLifeCycle<AppTestBo>())
                ?.subscribe(getObserver())
    }

    private fun getObserver(): Observer<AppTestBo> {
        return object : Observer<AppTestBo> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(appTestBo: AppTestBo) {
                Log.d(tag, appTestBo.toString())
                startCorrespondAct(appTestBo.code)
            }

            override fun onError(e: Throwable) {
                Log.w(tag, "onError", e)
            }

            override fun onComplete() {
                Log.d(tag, "onComplete")
            }
        }
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

    private fun startCorrespondAct(internalCode: String) {
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