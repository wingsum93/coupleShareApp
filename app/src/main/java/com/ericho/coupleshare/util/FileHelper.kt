package com.ericho.coupleshare.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.ericho.coupleshare.App
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Arrays
import java.util.UUID


/**
 * Created by steve_000 on 18/7/2017.
 * for project coupleShareApp
 * package name com.ericho.coupleshare.util
 */
class FileHelper constructor(val context:Context){



    @Throws(IOException::class)
    fun convertUri(uri:Uri):File{
        val chunkSize = 1024  // We'll read in one kB at a time
        val imageData = ByteArray(chunkSize)
        var out: OutputStream? = null
        var inputStream: InputStream? = null
        try {
            var fileName = getRandomFileName_jpg()

            val createDir =  context.cacheDir
            if (!createDir.exists()) {
                createDir.mkdir()
            }
            inputStream = App.context!!.contentResolver.openInputStream(uri)
            val file = File(createDir, fileName)
            file.createNewFile()
            out = FileOutputStream(file)

            var bytesRead: Int = inputStream.read(imageData)
            while ((bytesRead ) > 0) {
                out.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)))
                bytesRead = inputStream.read(imageData)
            }
            out.close()
            return file
        }catch (e:Exception){
            Timber.w(e)
            throw IOException(e)
        }finally {
            out?.close()
        }


    }

    fun convertUriToFile(uris:List<Uri>):List<File>{
        return uris.map { convertUri(it) }.toList()
    }


    fun getFilename(uri:Uri): String? {
        /*  Intent intent = getIntent();
    String name = intent.getData().getLastPathSegment();
    return name;*/
        var fileName: String? = null
        val scheme = uri.scheme
        if (scheme == "file") {
            fileName = uri.lastPathSegment
        } else if (scheme == "content") {
            val proj = arrayOf(MediaStore.Video.Media.TITLE)
            val contentUri: Uri? = null
            val cursor = context.getContentResolver().query(uri, proj, null, null, null)
            if (cursor != null && cursor!!.getCount() !== 0) {
                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
                cursor!!.moveToFirst()
                fileName = cursor!!.getString(columnIndex)
            }
        }
        return fileName
    }
    fun getRandomFileName_jpg():String{
        return UUID.randomUUID().toString()+".jpg"
    }


}