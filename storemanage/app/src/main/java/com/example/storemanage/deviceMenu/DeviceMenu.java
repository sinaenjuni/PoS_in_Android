package com.example.storemanage.deviceMenu;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.storemanage.R;
import com.example.storemanage.mainDevice.MainDeviceMain;
import com.example.storemanage.storeMain.StoreActivity;
import com.example.storemanage.subDevice.SubDeviceMain;

public class DeviceMenu extends AppCompatActivity {

    private Button mainDeviceButton;
    private Button subDeviceButton;
    private Button manageDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_menu);

        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.RECEIVE_SMS}, 1);

        mainDeviceButton = (Button) findViewById(R.id.deviceButtonMain);
        subDeviceButton = (Button)findViewById(R.id.deviceButtonSub);
        manageDeviceButton = (Button)findViewById(R.id.device_button_manage);

        mainDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceMenu.this, MainDeviceMain.class));

            }
        });

        subDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceMenu.this, SubDeviceMain.class));
            }
        });

        manageDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeviceMenu.this, StoreActivity.class));
            }
        });

    }
}
