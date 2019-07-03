package com.example.storemanage.storeMain.settings;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storemanage.R;
import com.example.storemanage.model.SettintMenuModel;
import com.example.storemanage.model.TableModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StoreSettingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;

    static int mScreenWidth;
    static int mScreenHeight;
    static int mScreenCenterWidth;
    static int mScreenCenterHeight;

    private FloatingActionButton fab, fab1, fab2, fab3;

    DragSurfaceView dragSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);

        //DragSurfaceView dragSurfaceView = new DragSurfaceView(this);
        //setContentView(dragSurfaceView);

        dragSurfaceView = (DragSurfaceView)findViewById(R.id.store_setting_dragsufaceview);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenCenterWidth = mScreenWidth/2;
        mScreenCenterHeight = mScreenHeight/2;




    }



    @Override
    public void onClick(View v) {


        int id = v.getId();
        switch (id) {
            case R.id.fab:
                dragSurfaceView.saveTables();

                Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                dragSurfaceView.clearTable();

                Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:

                dragSurfaceView.addTwoTable();
                Toast.makeText(this, "add two", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab3:

                dragSurfaceView.addForeTable();
                Toast.makeText(this, "add fore", Toast.LENGTH_SHORT).show();
                break;
        }

    }



}