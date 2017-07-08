package com.ericho.coupleshare.frag;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.ericho.coupleshare.R;
import com.ericho.coupleshare.contant.LatLong;
import com.ericho.coupleshare.interf.FabListener;
import com.ericho.coupleshare.mvp.Location;
import com.ericho.coupleshare.mvp.LocationsContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by steve_000 on 21/6/2017.
 * for project CoupleShare
 * package name com.ericho.coupleshare.frag
 */

public class LocationShowFrag extends SupportMapFragment implements OnMapReadyCallback ,LocationsContract.View,FabListener{

    private LocationsContract.Presenter presenter;


    public static LocationShowFrag newInstance(){
        LocationShowFrag frag = new LocationShowFrag();
        Bundle bundle = new Bundle();
        frag.setArguments(bundle);
        frag.getMapAsync(frag);
        return frag;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatLong.HK_C_LAT,LatLong.HK_C_LONG),10f));
        googleMap.addMarker(new MarkerOptions().position(new LatLng(22.309111d, 114.174993d)).title("HK"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.change_server_address,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setPresenter(LocationsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLocations(List<Location> locations) {

    }

    @Override
    public void showTaskMarkedActive() {

    }

    @Override
    public boolean isActive() {
        return getActivity()!=null;
    }

    @Override
    public void showNoLocations() {

    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    @Override
    public void onAttachFloatingActionListener(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(View.GONE);
    }
}
