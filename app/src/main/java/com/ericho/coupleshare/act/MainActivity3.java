package com.ericho.coupleshare.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.adapter.HomePageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity3 extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final int REQ_LOGIN = 111;


    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;
    @BindView(R.id.viewPager)
    protected ViewPager viewPager;

    private HomePageAdapter homePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        boolean isLogined = getUserLogined();
        if(!isLogined){
            Intent intent = new Intent(this,LoginAct.class);
            startActivityForResult(intent,REQ_LOGIN);
        }

    }

    private void init() {
        ButterKnife.bind(this);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        homePageAdapter = new HomePageAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(homePageAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more,menu);
        return true;
    }

    private boolean getUserLogined() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQ_LOGIN:
                if(resultCode == Activity.RESULT_CANCELED){
                    Toast.makeText(this,"Login canceled",Toast.LENGTH_SHORT).show();
                }else {
                    loadUserData();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadUserData() {
        //load user name and server photo's in background....
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
