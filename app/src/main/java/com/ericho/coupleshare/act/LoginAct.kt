package com.ericho.coupleshare.act

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView
import com.ericho.coupleshare.App
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.R
import com.ericho.coupleshare.constant.AdsConstant
import com.ericho.coupleshare.frag.AlertDialogFrag
import com.ericho.coupleshare.http.model.BaseSingleResponse
import com.ericho.coupleshare.mvp.LoginContract
import com.ericho.coupleshare.mvp.data.LoginRepository
import com.ericho.coupleshare.mvp.presenter.LoginPresenter
import com.ericho.coupleshare.util.DrawableUtil
import com.ericho.coupleshare.util.AHttpHelper
import com.ericho.coupleshare.util.IntentConstant
import com.ericho.coupleshare.util.NetworkUtil
import com.google.android.gms.ads.MobileAds
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.act_login.*
import timber.log.Timber
import java.util.Date

/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class LoginAct:AppCompatActivity(), View.OnClickListener, LoginContract.View{


    val btn_login: Button by bindView(R.id.btn_login)
    val btn_register: Button by bindView(R.id.btn_register)
    val edt_username: EditText by bindView(R.id.edt_username)
    val edt_pw: EditText by bindView(R.id.edt_password)
    val progressBar: ProgressBar by bindView(R.id.progressBar)

    val img_state_light: ImageView by bindView(R.id.img_state_light)
    val txt_server_state: TextView by bindView(R.id.txt_server_state)

    var serverOn = false

    private var mLoginPresenter: LoginContract.Presenter? = null
    val loginRepository: LoginRepository by lazy{Injection.provideLoginRepository(this)}

    companion object {
        private val tag = "LoginAct"
        val RESULT_AUTHENTICATE = "RESULT_AUTHENTICATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_login)
        init()

    }

    private fun init() {

        Timber.d("init")
        mLoginPresenter = LoginPresenter(Injection.provideLoginRepository(this), this)
        mLoginPresenter?.start()
        mLoginPresenter?.registerOnTextChangeListener(edt_username, edt_pw,btn_login)

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener { v -> mLoginPresenter?.requestRegisterPage() }
        edt_pw.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val us_name = edt_username.text.toString()
                val pw = edt_pw.text.toString()
                mLoginPresenter?.login(us_name, pw)
                return@setOnEditorActionListener true
            }
            false
        }



        //for server state
        img_state_light.setImageDrawable(DrawableUtil.getDrawble(this,R.drawable.oval_red_24))
        txt_server_state.text = "Server is unavailable"

        fetchServerState()

        MobileAds.initialize(this, AdsConstant.APP_ID);

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.change_server_address, menu)
        menuInflater.inflate(R.menu.more, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_server_address -> {
                mLoginPresenter?.displayChangeServerAddressUi()
                return true
            }
            R.id.more -> {
                mLoginPresenter?.displayMoreUi()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View) {
        //login api
        val us_name = edt_username.text.toString()
        val pw = edt_pw.text.toString()
        mLoginPresenter?.login(us_name, pw)
    }


    override fun setPresenter(presenter: LoginContract.Presenter) {
        mLoginPresenter = presenter
    }

    override fun showLoadingIndicator(active: Boolean) {
        runOnUiThread { progressBar.visibility = if (active) View.VISIBLE else View.INVISIBLE }
    }

    override fun showLoginSuccess() {
        runOnUiThread {
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LoadingAct::class.java)
            intent.putExtra(IntentConstant.LOGIN_NAME,edt_username.text.toString())
            intent.putExtra(IntentConstant.LOGIN_PASSWORD,edt_password.text.toString())
            intent.putExtra(IntentConstant.LOGIN_TIME, Date())
            startActivity(intent)
            finish()
        }
    }

    override fun showLoginFailure(errorMessage: String?) {
        runOnUiThread {
            Timber.w("fail " + errorMessage)
            val dialog: AlertDialogFrag = AlertDialogFrag.newInstance("Error",errorMessage)
            dialog.show(supportFragmentManager,"error")
        }
    }

    override fun showRegisterPage() {
        runOnUiThread { startActivity(Intent(this, RegisterAct::class.java)) }
    }

    override fun showChangeServerAddressUi() {
        runOnUiThread { startActivity(Intent(this, ChangeServerDialog::class.java)) }
    }

    override fun showMoreUi() {
        runOnUiThread { startActivity(Intent(this, TestMainAct::class.java)) }
    }

    override fun showLoginButtonState(enable: Boolean) {
        runOnUiThread { btn_login.isEnabled = enable }
    }

    override fun resetLoginField() {
        runOnUiThread {
            edt_username.text = null
            edt_pw.text = null
        }
    }

    fun fetchServerState(){

        val http : AHttpHelper<BaseSingleResponse<String>>
        = AHttpHelper.Builder<BaseSingleResponse<String>>()
                .setFail { call, ioException ->
                    img_state_light.setImageDrawable(DrawableUtil.getDrawble(this,R.drawable.oval_red_24))
                    txt_server_state.text = "Server Off"
                    serverOn = false
                }.setTransformMethod { App.gson.fromJson(it,object :TypeToken<BaseSingleResponse<String>>(){}.type) }
                .setSuccessMethod { call, baseSingleResponse ->
                    img_state_light.setImageDrawable(DrawableUtil.getDrawble(this,R.drawable.oval_green_24))
                    txt_server_state.text = "Server Online"
                    serverOn = true
                    if (loginRepository.isLogin(this)) {
                        showLoginSuccess()
                    }
                }
                .build()
        val req = NetworkUtil.getFetchServerRequest();
        http.run(req)
    }
}