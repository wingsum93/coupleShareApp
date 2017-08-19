package com.ericho.coupleshare.loc

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.ericho.coupleshare.act.LoginAct
import com.ericho.coupleshare.mvp.data.LoginRepository
import com.ericho.coupleshare.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

/**
 * Created by steve_000 on 11/8/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.loc
 */
@RunWith(AndroidJUnit4::class)
class LoginTest {



  @Rule
  @JvmField
  val mActivity = ActivityTestRule(LoginAct::class.java)

  @Mock
  val mRepository:LoginRepository? = null



  @Before
  fun registerIdlingResource() {
    Espresso.registerIdlingResources(
            EspressoIdlingResource.getIdlingResource())
  }
  @Test
  fun xxx(){
    Intent()
//    mActivity.launchActivity()
  }


  @After
  fun after(){
      Espresso.unregisterIdlingResources(
              EspressoIdlingResource.getIdlingResource())

  }
}