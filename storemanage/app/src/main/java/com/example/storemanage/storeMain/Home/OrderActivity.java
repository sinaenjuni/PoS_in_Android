package com.example.storemanage.storeMain.Home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.storemanage.R;
import com.example.storemanage.model.OrderItemModel;
import com.example.storemanage.model.OrderedListModel;
import com.example.storemanage.model.SettintMenuModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    RecyclerView gridRecyclerView;
    List<OrderItemModel> orders;

    RecyclerView listRecyclerView;
    List<OrderedListModel> orderList;

    String tableId;

    TextView textViewSum;
    Button buttonEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        tableId = intent.getStringExtra("id");

        Log.e("onCreate: ", tableId );

        orders = new ArrayList<>();
        orderList = new ArrayList<>();
        textViewSum = (TextView) findViewById(R.id.orderedlist_sum);

        gridRecyclerView = (RecyclerView)findViewById(R.id.orderactivity_recyclerview);
        gridRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        gridRecyclerView.setAdapter(new GridOrderActivityRecyclerViewAdapter());

        listRecyclerView = (RecyclerView)findViewById(R.id.orderactivity_recyclerview_Orderedlist);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView.setAdapter(new ListOrderActivityRecyclerViewAdapter());

        buttonEnter = (Button)findViewById(R.id.orderactivity_button_enter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class GridOrderActivityRecyclerViewAdapter extends RecyclerView.Adapter {

        public GridOrderActivityRecyclerViewAdapter() {

            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                    .child("menus").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    orders.clear();
                    OrderItemModel orderItemModel;
                    SettintMenuModel settintMenuModel;

                    for(DataSnapshot d: dataSnapshot.getChildren()){
                        orderItemModel = new OrderItemModel();

                        settintMenuModel = d.getValue(SettintMenuModel.class);

                        orderItemModel.setMenuOrderName(settintMenuModel.getMenuName());
                        orderItemModel.setMenuOrderPrice(settintMenuModel.getMenuPrice());

                        orders.add(orderItemModel);
                    }
                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ordermenu, viewGroup, false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            ((CustomViewHolder)viewHolder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                            .child("orderedList").child(tableId).push().setValue(
                                    new OrderedListModel(orders.get(i).getMenuOrderName(), orders.get(i).getMenuOrderPrice()));


                }
            });

            ((CustomViewHolder)viewHolder).textViewName.setText(orders.get(i).getMenuOrderName());
            ((CustomViewHolder)viewHolder).textViewPrice.setText(orders.get(i).getMenuOrderPrice());

        }

        @Override
        public int getItemCount() {
            return orders.size();
        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView textViewName;
        TextView textViewPrice;

        public CustomViewHolder(View view) {
            super(view);

            layout = (LinearLayout)view.findViewById(R.id.orderitem_layout);
            textViewName = (TextView) view.findViewById(R.id.orderitem_textview_name);
            textViewPrice = (TextView) view.findViewById(R.id.orderitem_textview_price);


        }
    }

    private class ListOrderActivityRecyclerViewAdapter extends RecyclerView.Adapter {


        public ListOrderActivityRecyclerViewAdapter() {

            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                    .child("orderedList").child(tableId).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    orderList.clear();
                    OrderedListModel orderedListModel;
                    double tempSum = 0;
                    for(DataSnapshot d : dataSnapshot.getChildren()){

                        orderedListModel = d.getValue(OrderedListModel.class);
                        orderedListModel.setOrderListId(d.getKey());
                        orderList.add(orderedListModel);

                        tempSum += Double.parseDouble(orderedListModel.getOrderListPrice());

                        textViewSum.setText(tempSum + "");
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ordered, viewGroup, false);

            return new CustomOrderedListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            ((CustomOrderedListViewHolder)viewHolder).textViewName.setText(orderList.get(i).getOrderListName());
            ((CustomOrderedListViewHolder)viewHolder).textViewPrice.setText(orderList.get(i).getOrderListPrice());
            ((CustomOrderedListViewHolder)viewHolder).buttonCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("onClick: ", "삭제 이벤트 발생");
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                            .child("orderedList").child(tableId).child(orderList.get(i).getOrderListId()).removeValue();

                    textViewSum.setText("0");
                }
            });
        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }

        private class CustomOrderedListViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName;
            TextView textViewPrice;
            Button buttonCancle;

            public CustomOrderedListViewHolder(View view) {
                super(view);

                textViewName = (TextView) view.findViewById(R.id.orderedlist_textview_name);
                textViewPrice = (TextView) view.findViewById(R.id.orderedlist_textview_price);
                buttonCancle = (Button) view.findViewById(R.id.orderedlist_button_cancle);

            }
        }
    }
}
