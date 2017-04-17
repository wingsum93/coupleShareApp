package com.ericho.coupleshare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ericho.coupleshare.act.LoginAct;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_LOGIN = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isLogined = getUserLogined();
        if(!isLogined){
            Intent intent = new Intent(this,LoginAct.class);
            startActivityForResult(intent,REQ_LOGIN);
        }
    }

    private boolean getUserLogined() {
        return false;
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
}
