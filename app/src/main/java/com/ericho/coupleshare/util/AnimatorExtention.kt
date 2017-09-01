package com.ericho.coupleshare.util

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder

/**
 * Created by steve_000 on 30/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
fun PropertyValuesHolder.toAnimator(target:Any):Animator{
  val z = ObjectAnimator.ofPropertyValuesHolder(target,this)
  return z
}