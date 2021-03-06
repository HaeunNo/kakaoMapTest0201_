package com.example.kakaomaptest0201;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.opengl.alt.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.kakaomaptest0201.Meta.Documents;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import javax.crypto.spec.GCMParameterSpec;


public class MainActivity extends AppCompatActivity {


    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    LayoutInflater inflater;
    //aćć“ć

    double latitude = 35.160622891050146;
    double longitude = 126.87957687879235;

    Button btn;


    // GPSTracker class
    private GpsInfo gps;
    List<Address> addressList;
    private KeywordRepository keywordRepository;


    ArrayList<Documents> lottoList = new ArrayList<>();
    ArrayList<MapPOIItem> mapPOIItemArrayList = new ArrayList<>();

    Boolean ck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapView mapView = new MapView(MainActivity.this);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        ViewGroup mapViewContainer = findViewById(R.id.map_view);

        btn = findViewById(R.id.btn);



        mapViewContainer.addView(mapView);


        keywordRepository = new KeywordRepository();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ź¶ķ ģģ²­ģ ķ“ģ¼ ķØ
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gps = new GpsInfo(MainActivity.this);



                // GPS ģ¬ģ©ģ ė¬“ ź°ģ øģ¤źø°
                if (gps.isGetLocation()) {
                    int tagNum = 0;
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    keywordRepository.retrieveData(MainActivity.this, longitude, latitude);

                        Log.v("ģ¢ķź°", latitude + "," + longitude);
                             mapView.removeAllPOIItems();


                        for (int i = 0; i < lottoList.size(); i++) {
                            mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(getApplicationContext()));

                            MapPOIItem customMarker = new MapPOIItem();

                            double x = Double.parseDouble(lottoList.get(i).getY());
                            double y = Double.parseDouble(lottoList.get(i).getX());
                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
                            customMarker.setItemName(lottoList.get(i).getPlace_name());
                            customMarker.setTag(tagNum++);
                            customMarker.setMapPoint(mapPoint);
                            customMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // źø°ė³øģ¼ė” ģ ź³µķė BluePin ė§ģ»¤ ėŖØģ.
                            customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // ė§ģ»¤ė„¼ ķ“ė¦­ķģė, źø°ė³øģ¼ė” ģ ź³µķė RedPin ė§ģ»¤ ėŖØģ.
                            customMarker.getCustomCalloutBalloon();
                            customMarker.setShowDisclosureButtonOnCalloutBalloon(true);
                            mapView.addPOIItem(customMarker);

                            mapPOIItemArrayList.add(customMarker);
                            ck = true;

                            Toast.makeText(getApplicationContext(), "ģ§ģ" + tagNum
                                    , Toast.LENGTH_SHORT).show();
                        }


                        try {
                            addressList = geocoder.getFromLocation(latitude, longitude, 5);
                            Log.v("ģ£¼ģ", addressList.toString());
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(addressList.get(0).getAddressLine(0));
                            marker.setTag(tagNum++);
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // źø°ė³øģ¼ė” ģ ź³µķė BluePin ė§ģ»¤ ėŖØģ.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // ė§ģ»¤ė„¼ ķ“ė¦­ķģė, źø°ė³øģ¼ė” ģ ź³µķė RedPin ė§ģ»¤ ėŖØģ.
                            mapView.addPOIItem(marker);

                            // gps ķģ¬ ģģ¹ė” ģ¤ė ė²Ø ė³ź²½
                            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 2, true);


                            Log.v("ģģøģ£¼ģ", addressList.get(1).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.v("ģ£¼ģģ¤ķØ", "ģ¤ķØ");
                        }


                    } else {
                        // GPS ė„¼ ģ¬ģ©ķ ģ ģģ¼ėÆė”
                        gps.showSettingsAlert();
                    }
                }
        });


        while(ck) {
            if (mapPOIItemArrayList.size() >= 0) {
                Toast.makeText(getApplicationContext(), "ifė¬ø", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mapPOIItemArrayList.size(); i++) {
                    mapView.addPOIItem(mapPOIItemArrayList.get(i));
                }
            }
            ck= false;
        }







    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // ģ ķė²ķø ź¶ķ ģģ²­
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    public void retrieveOnSuccess(List<Documents> data) {
        int getListSize = data.size();
        for (int i = 0; i < getListSize; i++) {
            String place_name = data.get(i).getPlace_name();
            String address_name = data.get(i).getAddress_name();
            String phone = data.get(i).getPhone();
            String x = data.get(i).getX();
            String y = data.get(i).getY();
//            Log.v("ģģ  ģ“ė¦",data.get(i).getPlace_name());
//            Log.v("ģģ  ģ£¼ģ",data.get(i).getAddress_name());
//            Log.v("ģģ  ģ ķė²ķø",data.get(i).getPhone());
//            Log.v("ģģ  ģ¢ķ",data.get(i).getX()+","+data.get(i).getY());
            lottoList.add(new Documents(place_name,address_name,phone,x,y));
        }
    }

}
