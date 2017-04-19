package com.ericho.coupleshare.act;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.ericho.coupleshare.BuildConfig;
import com.ericho.coupleshare.R;
import com.ericho.coupleshare.ServerUrlAdapter;
import com.ericho.coupleshare.contant.Key;
import com.ericho.coupleshare.setting.ServerAddress;
import com.ericho.coupleshare.setting.model.ServerBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EricH on 18/4/2017.
 */

public class ChangeServerDialog extends RxLifecycleAct implements View.OnClickListener,
        View.OnFocusChangeListener, AdapterView.OnItemClickListener {


    @BindView(R.id.edt_server)
    EditText edt_server;
    @BindView(R.id.gridView)
    protected GridView gridView;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

    final String t = "ChangeServerDialog";
    private SharedPreferences preferences;
    private List<ServerBean> serverBeen;
    private ServerUrlAdapter adapter;
    @Nullable
    private EditText targentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyView();
    }

    private void setMyView() {
        setContentView(R.layout.act_dialog_change_server);
        ButterKnife.bind(this);
        setTitle("Enter url and port number");
        initializeUiState();
        setListener();

        //
        preferences = getSharedPreferences(Key.pref_name, 0);
        String str2 = preferences.getString(Key.server_address, BuildConfig.default_address);

        edt_server.setText(str2);


        serverBeen = prepareUrlObject();
        adapter = new ServerUrlAdapter(this, serverBeen);
        gridView.setAdapter(adapter);
    }

    private void initializeUiState() {
        //bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //view
    }

    private void setListener() {
        //listener

        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        //focus ch listener
        edt_server.setOnFocusChangeListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_ok:
                saveServerUrl();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
        }
    }

    private void saveServerUrl() {
        //prepare
        SharedPreferences setting = getSharedPreferences(Key.pref_name, 0);
        SharedPreferences.Editor editor = setting.edit();
        String dataUrl;
        //logic judge

        dataUrl = edt_server.getText().toString();

        //set data
        editor.putString(Key.server_address, dataUrl);
        //set redownload location
        editor.apply();
        finish();
    }

    //-------------------------------  target --------------------------------------------**//
    private void setTragetText(String s) {
        if (targentEditText == null) return;
        targentEditText.setText(s);
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        boolean isEditText = v instanceof EditText;
        if (!isEditText) return;
        if (hasFocus) {
            targentEditText = (EditText) v;
        } else {
            targentEditText = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ServerBean bean = adapter.getItem(position);
        setTragetText(bean.getUrl());
    }


    private List<ServerBean> prepareUrlObject() {
        List<ServerBean> res = new ArrayList<>();

        res.add(new ServerBean("Ho", ServerAddress.s_home_pc, R.drawable.mountain_64));
        res.add(new ServerBean("com", ServerAddress.s_com_pc, R.drawable.fire_orange_64));

        return res;
    }

}
