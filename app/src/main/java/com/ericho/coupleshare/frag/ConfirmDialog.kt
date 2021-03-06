package com.ericho.coupleshare.frag

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog


/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */
class ConfirmDialog : DialogFragment(){

    var title:String? = null
    var message:String? = null
    @JvmField
    var confirmRunnable :()->Unit = {}
    @JvmField
    var cancelRunnable :()->Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!=null){
            title = arguments.getString(FLAG_TITLE)
            message = arguments.getString(FLAG_MESSAGE)
        }else{
            throw UnsupportedOperationException()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(message)
                .setPositiveButton(android.R.string.ok, { _, _ ->
                    confirmRunnable.invoke()
                }).setNegativeButton(android.R.string.cancel,{ _, _ ->
                    cancelRunnable.invoke()
                })

        // Create the AlertDialog object and return it
        return builder.create()

    }

    fun setConfirmRunnable(runnable: ()->Unit) : ConfirmDialog {
        confirmRunnable = runnable
        return this
    }
    fun setCancelRunnable(runnable: ()->Unit) : ConfirmDialog {
        cancelRunnable = runnable
        return this
    }

    companion object {
        @JvmField
        val FLAG_TITLE:String = "AAA"
        val FLAG_MESSAGE:String = "BBB"
        @JvmStatic
        fun newInstance(title:String,message:String?): ConfirmDialog {
            val frag = ConfirmDialog()
            val args: Bundle = Bundle()
            args.putString(FLAG_TITLE,title)
            args.putString(FLAG_MESSAGE,message)
            frag.arguments = args
            return frag
        }
    }
}