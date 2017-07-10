package com.ericho.coupleshare

import android.support.test.runner.AndroidJUnit4
import android.test.AndroidTestCase
import com.ericho.coupleshare.http.model.BaseResponse
import com.ericho.coupleshare.mvp.Location
import com.ericho.coupleshare.mvp.data.LocationDataSource
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Type
import java.util.*

/**
 * Created by steve_000 on 10/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare
 */
@RunWith(AndroidJUnit4::class)
class DbTest : AndroidTestCase() {


    val src = "{\"status\":true,\"extra\":[{\"id\":1,\"username\":\"eric\",\"uploadBy\":\"eric\",\"attitude\":11.6,\"longitude\":11.552,\"latitude\":null,\"accurate\":12,\"collectDate\":1488643200000}],\"errorMessage\":null,\"otherMessage\":null}"

    @Test
    fun fff(){
        val gson =
                GsonBuilder()
                        .registerTypeAdapter( Date::class.java, JsonDeserializer<Date> { json, _, _ -> Date(json.asJsonPrimitive.asLong) })
                        .create()

        val type : Type = object : TypeToken<BaseResponse<Location>>() {}.type
        val base: BaseResponse<Location> = gson.fromJson(src,type)

        val list = base.extra

        val loc = list!![0]

        val str = gson.toJson(loc)
        val repository = Injection.provideLocationsRepository(App.context!!)

        repository.saveLocation(loc,object:LocationDataSource.SaveLocationCallback{
            override fun onLocationSave() {
            }

            override fun onLocationSaveFailure(t: Throwable) {
            }
        })

        println("$str")
    }

    fun testAAA(){

        val loc = Location()
        loc.id=1
        loc.accurate=999


    }
}