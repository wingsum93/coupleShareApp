package com.ericho.coupleshare.act

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.ericho.coupleshare.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

/**A welcome act, using the basic animation
 * Created by eric ho on 2017/7/2.
 */
class SplashActivity : AppCompatActivity() {

  val mFont: Typeface by lazy {  Typeface.createFromAsset(this.assets, "fonts/wt071.ttf") }


  var mAnimatorSet:AnimatorSet? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //设置全屏
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    setContentView(R.layout.activity_splash)
    initView()

  }


  private fun buildImageAnimator(img:ImageView): Animator {

    val alpha = PropertyValuesHolder.ofFloat("alpha", 0.1f, 1.0f)
    val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.1f, 1.0f);
    val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.1f, 1.0f);
    //for rotate
    val anim = PropertyValuesHolder.ofFloat( "rotation", 0f, 360f)

    val ob = ObjectAnimator.ofPropertyValuesHolder(img, alpha, scaleX,scaleY,anim);

    return ob
  }

  private fun initView() {
    //make all text view of my font

    tv_name.typeface = mFont
    tv_name_english.typeface = mFont

    mAnimatorSet = generateAnimatorSet()
    mAnimatorSet?.start()
    circle_layout.setOnClickListener {
      mAnimatorSet?.cancel()
      mAnimatorSet?.start()
    }

  }

  fun generateAnimatorSet():AnimatorSet{

    val animatorSet = AnimatorSet()
    val z = arrayListOf(buildImageAnimator(iv_icon_1),buildImageAnimator(iv_icon_2),buildImageAnimator(iv_icon_3))
    animatorSet.playSequentially(z)
    animatorSet.addListener(object :AnimatorListenerAdapter(){
      override fun onAnimationEnd(animation: Animator?) {
//        toast("animation end!")
        startActivity(intentFor<LoginAct>())

      }

      override fun onAnimationStart(animation: Animator?) {
        iv_icon_1.alpha = 0f
        iv_icon_2.alpha = 0f
        iv_icon_3.alpha = 0f
      }
    })
    animatorSet.duration = 1500
    return animatorSet
  }


}
