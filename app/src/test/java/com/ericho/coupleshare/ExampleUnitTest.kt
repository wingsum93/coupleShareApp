package com.ericho.coupleshare

import com.ericho.coupleshare.bu.XXManager
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.spy

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    val mock = spy(XXManager::class.java)


    Mockito.`when`(mock.getOne()).thenReturn(123)

    val z = mock.getOne()

    Mockito.verify(mock,Mockito.atLeastOnce()).getOne()
    Mockito.verify(mock,Mockito.never()).getOne()


  }


}