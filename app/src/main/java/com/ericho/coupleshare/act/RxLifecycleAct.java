package com.ericho.coupleshare.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by steve_000 on 15/4/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */

public class RxLifecycleAct extends AppCompatActivity {
    private BehaviorSubject<ActivityEvent> subject = BehaviorSubject.create();

    public Observable<ActivityEvent> getEventObservable() {
        return subject;
    }

    public <T> LifecycleTransformer<T> bindToLifeCycle() {
        return RxLifecycleAndroid.bindActivity(getEventObservable());
    }

    public <T> LifecycleTransformer<T> bindToLifeCycle(ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(getEventObservable(), event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        subject.onNext(ActivityEvent.CREATE);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        subject.onNext(ActivityEvent.START);
        super.onStart();
    }

    @Override
    protected void onResume() {
        subject.onNext(ActivityEvent.RESUME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        subject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        subject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        subject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }

}
