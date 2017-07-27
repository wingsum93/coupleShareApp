package com.ericho.coupleshare.act

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import butterknife.bindView
import com.ericho.coupleshare.Injection
import com.ericho.coupleshare.R
import com.ericho.coupleshare.adapter.HomePageAdapter
import com.ericho.coupleshare.frag.LocationShowFrag
import com.ericho.coupleshare.interf.FabListener
import com.ericho.coupleshare.interf.PermissionListener
import com.ericho.coupleshare.mvp.data.LoginRepository
import com.ericho.coupleshare.mvp.presenter.LocationsPresenter
import com.ericho.coupleshare.service.LocationMonitorSer
import timber.log.Timber


/**
 * Created by steve_000 on 8/7/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.act
 */
class MainActivity3: BasePermissionActivity(), ViewPager.OnPageChangeListener  {



    val toolbar: Toolbar by bindView(R.id.toolbar)
    val tabLayout: TabLayout by bindView(R.id.tabLayout)
    val viewPager: ViewPager by bindView(R.id.viewPager)
    val floatingActionButton: FloatingActionButton by bindView(R.id.fab)

    var loginRepository:LoginRepository? = null

    var mLocationPresenter:LocationsPresenter? = null

    private var homePageAdapter: HomePageAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        doNormalWork()

    }

    private fun doNormalWork() {
        this.checkSelfPermission(REQ_LOCATION_UPDATE,android.Manifest.permission.ACCESS_COARSE_LOCATION.toList(),
                listener = object : PermissionListener{
            override fun onGranted() {
                Toast.makeText(baseContext,"granted ",Toast.LENGTH_SHORT).show()
                startService(Intent(this@MainActivity3,LocationMonitorSer::class.java))
            }

            override fun onDenied(deniedPermission: List<String>) {
                val dialog = com.ericho.coupleshare.frag.AlertDialogFrag.newInstance("Error","the following permission deny:\n" + "$deniedPermission")
                dialog.show(supportFragmentManager,"error")
            }
        })


    }

    private fun init() {

        setSupportActionBar(toolbar)
        homePageAdapter = HomePageAdapter(supportFragmentManager, this)
        viewPager.adapter = homePageAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(this)
        this.onPageSelected(0)


        //for presneter
        mLocationPresenter = LocationsPresenter(homePageAdapter!!.getItem(2) as LocationShowFrag)
        mLocationPresenter!!.start()

        loginRepository = Injection.provideLoginRepository(this.applicationContext)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.more, menu)
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.more -> openSettingAct()
            R.id.menu_logout -> {
                loginRepository?.logout(this)
                startActivity(Intent(this,LoginAct::class.java))
                this.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettingAct() :Boolean{
        startActivity(Intent(this,SettingsActivity::class.java))
        return true
    }

    private fun getUserLogined(): Boolean {
        return loginRepository!!.isLogin(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQ_LOGIN -> if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Login canceled", Toast.LENGTH_SHORT).show()
            } else {
                loadUserData()
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun loadUserData() {
        //load user name and server photo's in background....
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        invalidateOptionsMenu()
        Timber.d("onPageSelected pos $position")
        val fragment = homePageAdapter?.getItem(position)
        if (fragment is FabListener) {
            val lis = fragment as FabListener
            floatingActionButton.visibility = View.VISIBLE
            floatingActionButton.setOnClickListener(null)
            lis.onAttachFloatingActionListener(floatingActionButton)
        }
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }



    override fun onPageScrollStateChanged(state: Int) {

    }


    companion object {
        private val REQ_LOGIN = 111
        private val REQ_LOCATION_UPDATE = 3333

    }
}

