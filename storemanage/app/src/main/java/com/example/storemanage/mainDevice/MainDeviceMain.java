package com.example.storemanage.mainDevice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.storemanage.R;
import com.example.storemanage.model.SMSFunction;

public class MainDeviceMain extends AppCompatActivity {
    private Intent serviceIntent;
    SMSFunction smsFunction;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device_main);

        getFragmentManager().beginTransaction().replace(R.id.main_framelayout, new WaitingFragment()).commit();


        bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.e("text 1 : " , menuItem.toString());

                return false;
            }
        });


        smsFunction = new SMSFunction(MainDeviceMain.this);

        //앱 실행으로 서비스를 킴, 메인 디바이스 전용 모드 진입시 호출
        if(SMSFunction.serviceIntent == null) {
            serviceIntent = new Intent(getApplicationContext(), SMSFunction.class);
            startService(serviceIntent);
        } else {
            serviceIntent = SMSFunction.serviceIntent; //getInstance().getApplication();
            Toast.makeText(getApplicationContext(), "Already Sevice Running", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}
