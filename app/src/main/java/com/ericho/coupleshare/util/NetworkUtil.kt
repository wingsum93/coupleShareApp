package com.ericho.coupleshare.util

import android.content.Context
import com.ericho.coupleshare.App
import com.ericho.coupleshare.constant.Constant
import com.ericho.coupleshare.contant.WebAddress
import com.ericho.coupleshare.network.LoggingInterceptor
import com.ericho.coupleshare.network.UserInfoInterceptor
import com.ericho.coupleshare.inputmodel.UploadLocationInputModel
import com.ericho.coupleshare.inputmodel.ViewLocationInputModel
import com.ericho.coupleshare.mvp.Location
import okhttp3.Cache
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by steve_000 on 9/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.util
 */
object NetworkUtil {
    @Volatile
    private var okhttpClient: OkHttpClient? = null

    val MAX_CACHE_SIZE: Long = 1024 * 1024 * 45
    private var cache = Cache(File(App.context!!.cacheDir, Constant.HTTP_CACHE_FOLDER_NAME), MAX_CACHE_SIZE)

    private val json = MediaType.parse("application/json; charset=utf-8")
    private val MediaType_Image = MediaType.parse("image/jpg")


    fun getOkhttpClient(): OkHttpClient {

        if (okhttpClient == null) {
            synchronized(NetworkUtil::class) {
                if (okhttpClient == null) {
                    okhttpClient = OkHttpClient.Builder()
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .cache(cache)
                            .addNetworkInterceptor(UserInfoInterceptor())
                            .addNetworkInterceptor(LoggingInterceptor())
                            .build()
                }
            }
        }

        return okhttpClient as OkHttpClient
    }

    fun getXXXRequest(ss: String): Request {
        val builder = Request.Builder()
        builder.addHeader("XXX", "XXX")
        //        builder.post()
        return builder.build()
    }

    fun execute(okHttpClient: OkHttpClient = getOkhttpClient(), request: Request): Call {
        return okHttpClient.newCall(request)
    }


    fun getUrl(context: Context, suffix: String): String {

        val s = ServerAddressUtil.getServerAddress(context) + suffix
        return s
    }


    @Throws(IOException::class)
    @Deprecated("should not use ")
    fun getResponseByRequest(call: Call): Response {
        val response = call.execute()
        if (!response.isSuccessful) throw IOException(response.message())
        return response
    }

    @Throws(IOException::class)
    fun parseResponseToString(response: Response): String {
        if (!response.isSuccessful) throw IOException(response.message())
        return response.body().string()
    }

    //------------------------------------------------------------------------------

    @Throws(IOException::class)
    fun registerAccount(context: Context, username: String, password: String): Request {
        val builder = FormBody.Builder()
        builder.add("username", username)
        builder.add("password", password)

        val b = Request.Builder()
        b.post(builder.build())
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_REGISTER)))
        return b.build()
    }

    @Throws(IOException::class)
    fun loginAccount(context: Context, username: String, password: String): Request {
        val builder = FormBody.Builder()
        builder.add("username", username)
        builder.add("password", password)

        val b = Request.Builder()
        b.post(builder.build())
        b.url(HttpUrl.parse(getUrl(context, WebAddress.API_LOGIN)))
        return b.build()
    }

    fun getLocationList(): Request {

        val model = ViewLocationInputModel()
        Timber.v(App.gson.toJson(model))
        val requestBody = RequestBody.create(json, App.gson.toJson(model))

        val b = Request.Builder()
        b.post(requestBody)
        b.url(HttpUrl.parse(getUrl(App.context!!, WebAddress.API_LOC_GET)))

        return b.build()
    }

    fun uploadLocation(location: Location): Request {

        val model = UploadLocationInputModel()
        model.locations.add(location)

        Timber.d(App.gson.toJson(model))
        val requestBody = RequestBody.create(json, App.gson.toJson(model))

        val b = Request.Builder()
        b.post(requestBody)
        b.url(HttpUrl.parse(getUrl(App.context!!, WebAddress.API_LOC_UPLOAD)))
        return b.build()
    }

    fun status_add(title: String, content: String, file: File): Request {
        val body = MultipartBody.Builder()
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("photo",
                        file.name,
                        RequestBody.create(MediaType_Image, file))
                .build()

        val req: Request = Request.Builder()
                .url(getUrl(App.context!!, WebAddress.API_STATUS_CREATE))
                .post(body)
                .build()
        return req
    }

    fun status_get(): Request {
        val req: Request = Request.Builder()
                .url(getUrl(App.context!!, WebAddress.API_STATUS_VIEW))
                .get()
                .build()
        return req
    }

    fun getFetchServerRequest(): Request {
        val req: Request = Request.Builder()
                .url(getUrl(App.context!!, WebAddress.API_SERVER_HEALTH))
                .get()
                .build()
        return req
    }

    fun photo_get(): Request {
        val req: Request = Request.Builder()
                .url(getUrl(App.context!!, WebAddress.API_PHOTO_VIEW))
                .get()
                .build()
        return req
    }

    fun photo_upload(file: File, tags: Array<String>?): Request {
        var tagString = ""
        if (tags?.isNotEmpty() ?: false) {
            tags!!.forEachIndexed { index, s ->
                tagString += s.trim()
                if (index != tags.lastIndex) tagString += ","
            }
        }

        val body = MultipartBody.Builder()
                .addFormDataPart("photo",
                        file.name,
                        RequestBody.create(MediaType_Image, file))
                .addFormDataPart("tags", tagString)
                .build()

        val req: Request = Request.Builder()
                .url(getUrl(App.context!!, WebAddress.API_PHOTO_UPLOAD))
                .post(body)
                .build()
        return req
    }

}