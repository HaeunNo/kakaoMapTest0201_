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
    //aㅁㄴㅇ

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
                // 권한 요청을 해야 함
                if (!isPermission) {
                    callPermission();
                    return;
                }
                gps = new GpsInfo(MainActivity.this);



                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {
                    int tagNum = 0;
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    keywordRepository.retrieveData(MainActivity.this, longitude, latitude);

                        Log.v("좌표값", latitude + "," + longitude);
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
                            customMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
                            customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            customMarker.getCustomCalloutBalloon();
                            customMarker.setShowDisclosureButtonOnCalloutBalloon(true);
                            mapView.addPOIItem(customMarker);

                            mapPOIItemArrayList.add(customMarker);
                            ck = true;

                            Toast.makeText(getApplicationContext(), "진입" + tagNum
                                    , Toast.LENGTH_SHORT).show();
                        }


                        try {
                            addressList = geocoder.getFromLocation(latitude, longitude, 5);
                            Log.v("주소", addressList.toString());
                            MapPOIItem marker = new MapPOIItem();
                            marker.setItemName(addressList.get(0).getAddressLine(0));
                            marker.setTag(tagNum++);
                            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            mapView.addPOIItem(marker);

                            // gps 현재 위치로 줌레벨 변경
                            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 2, true);


                            Log.v("상세주소", addressList.get(1).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.v("주소실패", "실패");
                        }


                    } else {
                        // GPS 를 사용할수 없으므로
                        gps.showSettingsAlert();
                    }
                }
        });


        while(ck) {
            if (mapPOIItemArrayList.size() >= 0) {
                Toast.makeText(getApplicationContext(), "if문", Toast.LENGTH_SHORT).show();
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

    // 전화번호 권한 요청
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
//            Log.v("상점 이름",data.get(i).getPlace_name());
//            Log.v("상점 주소",data.get(i).getAddress_name());
//            Log.v("상점 전화번호",data.get(i).getPhone());
//            Log.v("상점 좌표",data.get(i).getX()+","+data.get(i).getY());
            lottoList.add(new Documents(place_name,address_name,phone,x,y));
        }
    }

}
