package com.example.storemanage.storeMain;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.storemanage.R;
import com.example.storemanage.model.DrawTableModel;
import com.example.storemanage.model.TableModel;
import com.example.storemanage.storeMain.Home.HomeMenuTouchInterface;
import com.example.storemanage.storeMain.Home.HomeTableCanvas;
import com.example.storemanage.storeMain.Home.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeMenuFragment extends Fragment implements HomeMenuTouchInterface {

    HomeTableCanvas homeTableCanvas;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        homeTableCanvas = (HomeTableCanvas)view.findViewById(R.id.home_fragment_canvas);
        homeTableCanvas.setOnHomeMenuTouchInterface(this);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                homeTableCanvas.onTouch(event);
                return true;
            }
        });


        return view;
    }

    @Override
    public void onMenuTouch(String string) {

        startActivity(new Intent(view.getContext(), OrderActivity.class).putExtra("id", string));
        Log.e("onMenuTouch: ", "인터페이스 정상 호출!!" + string);
    }
}

