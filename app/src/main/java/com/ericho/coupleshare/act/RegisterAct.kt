package com.ericho.coupleshare.act

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.bindView
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.R
import com.ericho.coupleshare.frag.AlertDialogFrag
import com.ericho.coupleshare.http.retrofit2.UserService
import com.ericho.coupleshare.mvp.RegisterContract
import com.ericho.coupleshare.mvp.presenter.RegisterPresenter

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
open class RegisterAct:RxLifecycleAct(), RegisterContract.View, View.OnClickListener {
    private val tag = "RegisterAct"

    val edt_username: EditText by bindView(R.id.edt_username)
    val edt_pw: EditText by bindView(R.id.edt_password)
    val btn_action: Button by bindView(R.id.btn_action)
    val progressBar: ProgressBar by bindView(R.id.progressBar)


    private val userService: UserService? = null

    private var mPresenter: RegisterContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_register)
        init()

    }

    private fun init() {

        mPresenter = RegisterPresenter(Injection.provideLoginRepository(this), this)
        mPresenter!!.assignOnTextChangeListener(edt_username,edt_pw)
        btn_action.setOnClickListener(this)

        edt_pw.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val us_name = edt_username.text.toString()
                val pw = edt_pw.text.toString()
                mPresenter?.register(us_name, pw)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun onClick(v: View) {
        mPresenter?.register(edt_username.text.toString(), edt_pw.text.toString())
    }

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoadingIndicator(active: Boolean) {
        runOnUiThread { progressBar.visibility = if (active) View.VISIBLE else View.INVISIBLE }
    }

    override fun showRegisterSuccess() {
        runOnUiThread { Toast.makeText(this, "rgister Success", Toast.LENGTH_LONG).show() }
    }

    override fun showRegisterFailure(errorMessage: String) {
        runOnUiThread {
            val dialog: AlertDialogFrag = AlertDialogFrag.newInstance("Error",errorMessage)
            dialog.show(supportFragmentManager,"error")
        }
    }

    override fun showRegisterButtonState(enable: Boolean) {
        runOnUiThread { btn_action.isEnabled = enable }
    }
}