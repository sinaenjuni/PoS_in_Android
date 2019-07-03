package com.example.storemanage.storeMain.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.storemanage.R;
import com.example.storemanage.model.SettintMenuModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private MenuSettingRecyclerViewAdapter adapter = new MenuSettingRecyclerViewAdapter();

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_setting);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);



        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.activity_menu_setting_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {


        int id = v.getId();
        switch (id) {
            case R.id.fab:
                startActivity(new Intent(MenuSettingActivity.this, MenuSettingInput.class));

                Toast.makeText(this, "Floating Action Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                anim();
                Toast.makeText(this, "Button1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                anim();
                Toast.makeText(this, "Button2", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    class MenuSettingRecyclerViewAdapter extends RecyclerView.Adapter {
        List<SettintMenuModel> menus;

        public MenuSettingRecyclerViewAdapter() {

            menus = new ArrayList<>();

            String uid = FirebaseAuth.getInstance().getUid();
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").push().setValue(new SettintMenuModel("https://cdn.pixabay.com/photo/2016/02/26/11/42/toppokki-1223900_1280.jpg", "민철이네 떡볶이","14005","매웃만, 짱매웃만, 덜매운맛"));



            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //서버에서 넘어오는 데이터
                    menus.clear(); //누적 제거거
                    SettintMenuModel settintMenuModel;
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        //Log.e("menu: ", snapshot.getValue().toString());
                        //Log.e("dataSet :" , snapshot.getKey());

                        settintMenuModel = snapshot.getValue(SettintMenuModel.class);
                        settintMenuModel.setMenuID(snapshot.getKey());

                        //Log.e("dataSet :" , settintMenuModel.getMenuID());
                        menus.add(settintMenuModel);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus").addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    menus.clear(); //누적 제거거
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        menus.add(snapshot.getValue(SettintMenuModel.class));
//                        Log.e("user: ", snapshot.getValue().toString());
//                    }
//                    notifyDataSetChanged();
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu_setting, viewGroup, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder (RecyclerView.ViewHolder viewHolder, int i) {


            Glide.with(viewHolder.itemView.getContext())
                    .load(menus.get(i).getMenuImageURL())
                    //.apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder) viewHolder).imageViewImageURL);

            ((CustomViewHolder) viewHolder).textViewName.setText(menus.get(i).getMenuName());
            ((CustomViewHolder) viewHolder).textViewPrice.setText(menus.get(i).getMenuPrice());
            ((CustomViewHolder) viewHolder).textViewOption.setText(menus.get(i).getMenuOption());

            ((CustomViewHolder) viewHolder).buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uid = FirebaseAuth.getInstance().getUid();
                    Log.e("버튼 : ", v.getId() + "눌림");

                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("menus")
                            .child(menus.get(i).getMenuID())
                            .removeValue();
                }

            });

            //try {
//                Glide.with(viewHolder.itemView.getContext())
//                        .load(menus.get(i).getMenuImageURL())
//                        .apply(new RequestOptions().circleCrop())
//                        .into(((CustomViewHolder) viewHolder).imageViewImageURL);


//                   glide.load(menus.get(i).getMenuImageURL())
//                        .apply(new RequestOptions().circleCrop())
//                        .into(((CustomViewHolder) viewHolder).imageViewImageURL);
//
//
//
//            } catch (Exception e){
//                e.printStackTrace();
//                Log.e("널이라고:", "????");
//                Log.e("????? : ", menus.size() + "");
//
//                for (SettintMenuModel s : menus) {
//                    Log.e("URL", s.getMenuImageURL());
//                    Log.e("이름", s.getMenuName());
//                    Log.e("가격", s.getMenuPrice());
//                    Log.e("옵션", s.getMenuOption());
//                }
//            }
        }

        @Override
        public int getItemCount() {
            return menus.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageViewImageURL;
            private TextView textViewName;
            private TextView textViewPrice;
            private TextView textViewOption;
            private Button buttonDelete;

            private CustomViewHolder(View view) {
                super(view);
                imageViewImageURL = (ImageView)view.findViewById(R.id.item_menu_setting_imageview);
                textViewName = (TextView)view.findViewById(R.id.item_menu_setting_textview_name);
                textViewPrice = (TextView)view.findViewById(R.id.item_menu_setting_textview_price);
                textViewOption = (TextView)view.findViewById(R.id.item_menu_setting_textview_oprion);
                buttonDelete = (Button)view.findViewById(R.id.item_menu_setting_button_delete);
            }
        }
    }
}


