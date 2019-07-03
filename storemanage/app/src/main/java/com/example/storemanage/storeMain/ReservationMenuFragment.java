package com.example.storemanage.storeMain;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.storemanage.R;
import com.example.storemanage.model.ReservationModel;
import com.example.storemanage.model.WaitingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationMenuFragment extends Fragment {

    RecyclerView recyclerView;
    List<ReservationModel> reservations;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        reservations = new ArrayList<>();

        recyclerView = (RecyclerView)view.findViewById(R.id.reservation_fragment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new ReservationFragmentRecyclerViewAdapter());

        return view;
    }


    private class ReservationFragmentRecyclerViewAdapter extends RecyclerView.Adapter {

        public ReservationFragmentRecyclerViewAdapter() {

            String uid = FirebaseAuth.getInstance().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("reservationInfo")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //서버에서 넘어오는 데이터
                            ReservationModel reservationModel;

                            reservations.clear(); //누적 제거거
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                reservationModel = snapshot.getValue(ReservationModel.class);
                                reservationModel.setID(snapshot.getKey());
                                reservations.add(reservationModel);

//                        waitingList.add(snapshot.getValue(WaitingModel.class));

                                Log.e("count : ", reservations.size() + "");

                            }
                            notifyDataSetChanged();
                        }

//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("waitinglist").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    //서버에서 넘어오는 데이터
//
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        WaitingModel waitingModel = snapshot.getValue(WaitingModel.class);
//
////                        usermodels.add(snapshot.getValue(UserModel.class));
////                        Log.e("user: ", new WaitingModel() = snapshot.getValue());
//
//                    }
//
//
//                }



                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reservation, viewGroup, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            for(ReservationModel r : reservations) {
                ((CustomViewHolder) viewHolder).name.setText(r.getEditName());
                ((CustomViewHolder) viewHolder).menu.setText(r.getEditMenu());
                ((CustomViewHolder) viewHolder).people.setText(r.getEditMan());
                ((CustomViewHolder) viewHolder).time.setText(r.getEditTime());
                ((CustomViewHolder) viewHolder).phone.setText(r.getEditPhone());

                ((CustomViewHolder)viewHolder).agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(" ", "onClick: 수락버튼 눌림" );
                    }
                });

                ((CustomViewHolder)viewHolder).cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid = FirebaseAuth.getInstance().getUid();
                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("reservationInfo")
                                .child(reservations.get(i).getID()).removeValue();


                        Log.e(" ", "onClick: 취소버튼 눌림" );
                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            return reservations.size();
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        TextView time;
        TextView menu;
        TextView people;
        Button agree;
        Button cancle;

        public CustomViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.textview_name);
            phone = (TextView) view.findViewById(R.id.textview_phone);
            time = (TextView) view.findViewById(R.id.textview_time);
            menu = (TextView) view.findViewById(R.id.textview_menu);
            people = (TextView) view.findViewById(R.id.textview_people);
            agree = (Button) view.findViewById(R.id.button_agree);
            cancle = (Button) view.findViewById(R.id.button_cancle);
        }
    }
}
