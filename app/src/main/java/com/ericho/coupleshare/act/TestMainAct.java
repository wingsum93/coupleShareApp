package com.ericho.coupleshare.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.R2;
import com.ericho.coupleshare.adapter.TestMainRecyclerAdapter;
import com.ericho.coupleshare.setting.model.AppTestBo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by steve_000 on 28/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class TestMainAct extends RxLifecycleAct {
    private static final String tag = "TestMainAct";

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    TestMainRecyclerAdapter testMainRecyclerAdapter;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TestMainAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        List<AppTestBo> list = getMainList();
        testMainRecyclerAdapter = new TestMainRecyclerAdapter(this, list);
        recyclerView.setAdapter(testMainRecyclerAdapter);

        //
        testMainRecyclerAdapter.getPositionClicks()
                .debounce(300, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifeCycle())
                .subscribe(getObserver());
    }

    private Observer<AppTestBo> getObserver() {
        return new Observer<AppTestBo>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(AppTestBo appTestBo) {
                Log.d(tag, appTestBo.toString());
                startCorrespondAct(appTestBo.getCode());
            }

            @Override
            public void onError(Throwable e) {
                Log.w(tag, "onError", e);
            }

            @Override
            public void onComplete() {
                Log.d(tag, "onComplete");
            }
        };
    }

    private List<AppTestBo> getMainList() {
        List<AppTestBo> res = new ArrayList<>();
//        res.add(AppTestBo.create(TestQuerySCCAct.CODE, "SCC", "search scc"));
        res.add(AppTestBo.create(TestSelectPhotoAct.hint, "TE", "search Photo"));
        res.add(AppTestBo.create(TestAppBarAct.FLAG, "TE", "AppBar"));
        return res;
    }

    private void startCorrespondAct(String internalCode) {
        switch (internalCode) {
            case TestSelectPhotoAct.hint:
                startActivity(new Intent(this, TestSelectPhotoAct.class));
                break;
            case TestAppBarAct.FLAG:
                startActivity(new Intent(this, TestAppBarAct.class));
                break;

            default:
        }
    }

}
