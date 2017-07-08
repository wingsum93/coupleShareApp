package com.ericho.coupleshare.act

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
open class RxLifecycleAct:AppCompatActivity() {
    private val subject = BehaviorSubject.create<ActivityEvent>()

    fun getEventObservable(): Observable<ActivityEvent> {
        return subject
    }

    fun <T> bindToLifeCycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity<T>(getEventObservable())
    }

    fun <T> bindToLifeCycle(event: ActivityEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent<T, ActivityEvent>(getEventObservable(), event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        subject.onNext(ActivityEvent.CREATE)
        super.onCreate(savedInstanceState)
    }


    override fun onStart() {
        subject.onNext(ActivityEvent.START)
        super.onStart()
    }

    override fun onResume() {
        subject.onNext(ActivityEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        subject.onNext(ActivityEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        subject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    override fun onDestroy() {
        subject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
    }
}