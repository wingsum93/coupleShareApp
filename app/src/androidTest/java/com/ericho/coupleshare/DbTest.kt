package com.ericho.coupleshare

import android.support.test.runner.AndroidJUnit4
import android.test.AndroidTestCase
import com.ericho.coupleshare.bu.XXManager
import com.ericho.coupleshare.mvp.Location
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

/**
 * Created by steve_000 on 10/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
@RunWith(AndroidJUnit4::class)
class DbTest : AndroidTestCase() {


  val src = "{\"status\":true,\"extra\":[{\"id\":1,\"username\":\"eric\",\"uploadBy\":\"eric\",\"attitude\":11.6,\"longitude\":11.552,\"latitude\":null,\"accurate\":12,\"collectDate\":1488643200000}],\"errorMessage\":null,\"otherMessage\":null}"


  @Test
  fun testMokit() {
    val mApiManager = Mockito.spy(XXManager::class.java)


//    Mockito.`when`(mApiManager.getOne()).thenReturn(123).thenReturn(111)

    val z = mApiManager.getOne()
//    assert(z==123)
    Assert.assertTrue(z==123)

  }



  fun testAAA() {

    val loc = Location()
    loc.id = 1
    loc.accurate = 999


  }
}