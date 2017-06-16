package com.example.administrator.mydemo.ui.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mydemo.R;
import com.example.administrator.mydemo.adapter.MapStytleRV;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, MapStytleRV.SelectItem {


    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200;
    private TextView mMapStyle;
    private GoogleMap mMap;

    private static final int Normal = 0;
    private static final int Hybrid = 1;
    private static final int Satellite = 2;
    private static final int Terrain = 3;
    private static final int None = 4;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        mMapStyle = (TextView) findViewById(R.id.show_map_style);
        mMapStyle.setOnClickListener(this);

        //创建地图对象
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }


    //申请权限
    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        // A step later in the tutorial adds the code to get the device location.
    }

    //回调权限处理
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
//        updateLocationUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_map_style:
                setStylePopup();
                break;
        }
    }

    private void setStylePopup() {
        //创建视图对象
        View inflate = LayoutInflater.from(this).inflate(R.layout.view_popup_style, null);
        //设置视图view
        RecyclerView styleRV = (RecyclerView) inflate.findViewById(R.id.view_popup_style_rv);
        styleRV.setHasFixedSize(true);
        styleRV.setLayoutManager(new LinearLayoutManager(this));
        MapStytleRV adapter = new MapStytleRV(this);
        styleRV.setAdapter(adapter);

        adapter.setOnItemClick(this);

        //创建对象
        mPopupWindow = new PopupWindow(inflate, mMapStyle.getWidth(), WindowManager.LayoutParams.WRAP_CONTENT);
        //设置可以获得焦点
        mPopupWindow.setFocusable(true);
        //设置可以点击窗体以外
        mPopupWindow.setOutsideTouchable(true);
        //设置动画
        mPopupWindow.setAnimationStyle(R.style.popup_window_anim);
        //更新popupwindow的状态
        mPopupWindow.update();
        //显示在界面位置
        mPopupWindow.showAsDropDown(mMapStyle, 0, mMapStyle.getHeight());
    }

    @Override
    public void onItemClick(int item) {
        if (mMap == null) {
            Toast.makeText(this, "请开启Google服务", Toast.LENGTH_SHORT).show();
            return;
        }
        int type = GoogleMap.MAP_TYPE_NORMAL;
        switch (item) {
            case Normal:
                type = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case Hybrid:
                type = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case Satellite:
                type = GoogleMap.MAP_TYPE_SATELLITE;
                break;
            case Terrain:
                type = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            case None:
                type = GoogleMap.MAP_TYPE_NONE;
                break;
        }
        mMap.setMapType(type);
        mPopupWindow.dismiss();
    }
}
