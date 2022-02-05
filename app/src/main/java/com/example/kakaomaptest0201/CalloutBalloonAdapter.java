package com.example.kakaomaptest0201;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;

import net.daum.mf.map.api.MapPOIItem;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;


class CustomCalloutBalloonAdapter implements net.daum.mf.map.api.CalloutBalloonAdapter {
    private final View mCalloutBalloon;

    public CustomCalloutBalloonAdapter(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCalloutBalloon = layoutInflater.inflate(R.layout.custom_callout_balloon,null);
    }



    @Override
    public View getCalloutBalloon(MapPOIItem mapPOIItem) {
        ((ImageView) mCalloutBalloon.findViewById(R.id.img_star)).setImageResource(R.drawable.favourite);
        ((TextView) mCalloutBalloon.findViewById(R.id.tv_placeName)).setText(mapPOIItem.getItemName());
        ((TextView) mCalloutBalloon.findViewById(R.id.tv_cnt)).setText(mapPOIItem.getTag()+"");
        return mCalloutBalloon;
    }


    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }
}