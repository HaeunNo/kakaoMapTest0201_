// Generated by view binder compiler. Do not edit!
package com.example.kakaomaptest0201.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.kakaomaptest0201.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btn;

  @NonNull
  public final RelativeLayout mapView;

  private ActivityMainBinding(@NonNull RelativeLayout rootView, @NonNull Button btn,
      @NonNull RelativeLayout mapView) {
    this.rootView = rootView;
    this.btn = btn;
    this.mapView = mapView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn;
      Button btn = ViewBindings.findChildViewById(rootView, id);
      if (btn == null) {
        break missingId;
      }

      id = R.id.map_view;
      RelativeLayout mapView = ViewBindings.findChildViewById(rootView, id);
      if (mapView == null) {
        break missingId;
      }

      return new ActivityMainBinding((RelativeLayout) rootView, btn, mapView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}