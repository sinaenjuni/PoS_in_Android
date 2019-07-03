package com.example.storemanage.storeMain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.storemanage.R;
import com.example.storemanage.mainDevice.WaitingFragment;
import com.example.storemanage.model.DrawTableModel;
import com.example.storemanage.model.EventLogModel;
import com.example.storemanage.model.ReservationModel;
import com.example.storemanage.model.TableModel;
import com.example.storemanage.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class StoreActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Bitmap bitmap[];
    RecyclerView recyclerView;
    List<EventLogModel> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        bitmap = new Bitmap[3];

        bitmap[0] = BitmapFactory.decodeResource(getResources(), R.drawable.reservation_icon);
        bitmap[0] = Bitmap.createScaledBitmap(bitmap[0], 120,90,true);
        bitmap[1] = BitmapFactory.decodeResource(getResources(), R.drawable.order_icon);
        bitmap[1] = Bitmap.createScaledBitmap(bitmap[1], 120,90,true);
        bitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.wait_icon);
        bitmap[2] = Bitmap.createScaledBitmap(bitmap[2], 120,90,true);

        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new HomeMenuFragment()).commitAllowingStateLoss();

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.store_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new HomeMenuFragment()).commitAllowingStateLoss();
                        Log.e("text 1 : " , menuItem.toString());
                        break;

                    case R.id.action_reservation:
                        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new ReservationMenuFragment()).commitAllowingStateLoss();
                        Log.e("text 1 : " , menuItem.toString());
                        break;

                    case R.id.action_waiting:
                        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new WaitingFragment()).commitAllowingStateLoss();
                        Log.e("text 1 : " , menuItem.toString());
                        break;


                    case R.id.action_setting:
                        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new SettingMenuFragment()).commitAllowingStateLoss();
                        Log.e("text 1 : " , menuItem.toString());
                        break;

                    case R.id.action_community:
                        getFragmentManager().beginTransaction().replace(R.id.store_main_framelayout, new CommunityMenuFragment()).commitAllowingStateLoss();
                        Log.e("text 1 : " , menuItem.toString());
                        break;
                }
                return true;
            }
        });

        events = new ArrayList<>();


        recyclerView = (RecyclerView)findViewById(R.id.main_event_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getLayoutInflater().getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MainEventLogRecyclerView());
    }

    private class MainEventLogRecyclerView extends RecyclerView.Adapter {

        public MainEventLogRecyclerView() {
            events.clear();

            //추가된 데이터 한개만 가져오는 리스너
            String uid = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist")
                    .addChildEventListener(new ChildEventListener() {

                        EventLogModel eventLog;
                        WaitingModel waitingModel;

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            eventLog = new EventLogModel();
                            waitingModel = dataSnapshot.getValue(WaitingModel.class);
                            Log.e("onChildAdded: ", waitingModel.toString());
                            eventLog.setType("2");
                            eventLog.setMsg("[" + waitingModel.getPhoneNumber() + "] 님, [" + waitingModel.getPeople() + "] 명");
                            events.add(eventLog);
                            notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(events.size());
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("reservationInfo")
                    .addChildEventListener(new ChildEventListener() {

                        EventLogModel eventLog;
                        ReservationModel reservationModel;

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            //서버에서 넘어오는 데이터
                                eventLog = new EventLogModel();
                                reservationModel = dataSnapshot.getValue(ReservationModel.class);

                                eventLog.setType("0");
                                eventLog.setMsg("[" + reservationModel.getEditName() + "] 님, [" + reservationModel.getEditMan() + "] 명, ["
                                        + reservationModel.getEditPhone()+"]");
                                eventLog.setTime(reservationModel.getEditTime());
                                eventLog.setId(dataSnapshot.getKey());
                                events.add(eventLog);
                                notifyDataSetChanged();
                                recyclerView.smoothScrollToPosition(events.size());
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_eventlog, viewGroup, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            Log.e("onBindViewHolder: ", i + " : " +events.get(i).getMsg());
            Log.e("size : ", events.size() +"");

                    //((CustomViewHolder) viewHolder).type.setText(events.get(i).getType());
                    if(events.get(i).getType().equals("0")) {
                        ((CustomViewHolder) viewHolder).type.setImageBitmap(bitmap[0]); //예약
                    }
                    if(events.get(i).getType().equals("1")) {
                        ((CustomViewHolder) viewHolder).type.setImageBitmap(bitmap[1]);//주문
                    }
                    if(events.get(i).getType().equals("2")) {
                        ((CustomViewHolder) viewHolder).type.setImageBitmap(bitmap[2]);//대기
                    }
                    ((CustomViewHolder) viewHolder).msg.setText(events.get(i).getMsg());
                    ((CustomViewHolder) viewHolder).time.setText(events.get(i).getTime());
                    ((CustomViewHolder) viewHolder).buttonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            events.remove(i);
                            notifyDataSetChanged();
                        }
                    });

        }

        @Override
        public int getItemCount() {
            return events.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView type;
            TextView msg;
            TextView time;
            Button buttonDelete;

            public CustomViewHolder(View view) {
                super(view);
                type = (ImageView) view.findViewById(R.id.imageview_type);
                msg = (TextView) view.findViewById(R.id.textview_msg);
                time = (TextView) view.findViewById(R.id.textview_time);
                buttonDelete = (Button) view.findViewById(R.id.button_delete);

            }
        }
    }
}
