package com.ericho.coupleshare.act

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import com.ericho.coupleshare.BuildConfig
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.ServerUrlAdapter
import com.ericho.coupleshare.contant.Key
import com.ericho.coupleshare.http.ApiManager
import com.ericho.coupleshare.setting.ServerAddress
import com.ericho.coupleshare.setting.model.ServerBean
import kotlinx.android.synthetic.main.act_dialog_change_server.*
import java.util.*

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class ChangeServerDialog:RxLifecycleAct(),View.OnClickListener,
        View.OnFocusChangeListener, AdapterView.OnItemClickListener {


    internal val t = "ChangeServerDialog"
    private var preferences: SharedPreferences? = null
    private var serverBeen: List<ServerBean>? = null
    private var adapter: ServerUrlAdapter? = null
    private var targentEditText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMyView()
    }

    private fun setMyView() {
        setContentView(R.layout.act_dialog_change_server)
        title = "Enter url and port number"
        initializeUiState()
        setListener()

        //
        preferences = getSharedPreferences(Key.pref_name, 0)
        val str2 = preferences?.getString(Key.server_address, BuildConfig.default_address)

        edt_server.setText(str2)


        serverBeen = prepareUrlObject()
        adapter = ServerUrlAdapter(this, serverBeen)
        gridView.adapter = adapter
    }

    private fun initializeUiState() {
        //bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //view
        edt_server
        gridView
        btn_ok
        btn_cancel
    }

    private fun setListener() {
        //listener

        btn_ok.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)
        gridView.setOnItemClickListener(this)
        //focus ch listener
        edt_server.setOnFocusChangeListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> saveServerUrl()
            R.id.btn_cancel -> finish()
        }
    }

    private fun saveServerUrl() {
        //prepare
        val setting = getSharedPreferences(Key.pref_name, 0)
        val editor = setting.edit()
        val dataUrl: String
        //logic judge

        dataUrl = edt_server.text.toString()

        //set data
        editor.putString(Key.server_address, dataUrl)
        ApiManager.getInstance().changeBaseUrl(dataUrl)
        //set redownload location
        editor.apply()
        finish()
    }

    //-------------------------------  target --------------------------------------------**//
    private fun setTragetText(s: String?) {
        if (targentEditText == null) return
        targentEditText!!.setText(s)
    }


    override fun onFocusChange(v: View, hasFocus: Boolean) {
        val isEditText = v is EditText
        if (!isEditText) return
        if (hasFocus) {
            targentEditText = v as EditText
        } else {
            targentEditText = null
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val bean = adapter?.getItem(position)
        setTragetText(bean!!.url)
    }


    private fun prepareUrlObject(): List<ServerBean> {
        val res = ArrayList<ServerBean>()

        res.add(ServerBean("Ho", ServerAddress.s_home_pc, R.drawable.mountain_64))
        res.add(ServerBean("com", ServerAddress.s_com_pc, R.drawable.fire_orange_64))

        return res
    }
}