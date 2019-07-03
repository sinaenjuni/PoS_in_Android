package com.example.storemanage.storeMain.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.storemanage.R;
import com.example.storemanage.model.TableModel;
import com.example.storemanage.storeMain.HomeMenuFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeTableCanvas extends View{

    HomeMenuTouchInterface homeMenuTouchInterface;

    List<TableModel> tables;
    private Bitmap image[] = new Bitmap[6];

    public HomeTableCanvas(Context context) {
        super(context);
        init();
    }

    public HomeTableCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTableCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){

        image[0] = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        image[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rect);

        image[0] = Bitmap.createScaledBitmap(image[0], 200, 200, true); //이미지 확대
        image[1] = Bitmap.createScaledBitmap(image[1], 400, 200, true); //이미지 확대

        image[2] = BitmapFactory.decodeResource(getResources(), R.drawable.reserved_circle);
        image[3] = BitmapFactory.decodeResource(getResources(), R.drawable.reserved_rect);

        image[2] = Bitmap.createScaledBitmap(image[2], 200, 200, true); //이미지 확대
        image[3] = Bitmap.createScaledBitmap(image[3], 400, 200, true); //이미지 확대

        image[4] = BitmapFactory.decodeResource(getResources(), R.drawable.ordered_circle);
        image[5] = BitmapFactory.decodeResource(getResources(), R.drawable.ordered_rect);

        image[4] = Bitmap.createScaledBitmap(image[4], 200, 200, true); //이미지 확대
        image[5] = Bitmap.createScaledBitmap(image[5], 400, 200, true); //이미지 확대

        tables = new ArrayList<>();
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("tables").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //서버에서 넘어오는 데이터
                tables.clear(); //누적 제거거
                TableModel tableModel;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    //Log.e("menu: ", snapshot.getValue().toString());
                    //Log.e("dataSet :" , snapshot.getKey());

                    tableModel = snapshot.getValue(TableModel.class);
                    tableModel.setId(snapshot.getKey());
                    tableModel.setHomeMenuTouchInterface(homeMenuTouchInterface);

                    //Log.e("dataSet :" , settintMenuModel.getMenuID());
                    tables.add(tableModel);
                    Log.e("tables: ", tableModel.toString() + "");
                }

                invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint imagePaint = new Paint();
        imagePaint.setAntiAlias(true);

        for(TableModel tableModel : tables){

            if(tableModel.getInfo().equals("1")) {
                canvas.drawBitmap(tableModel.getPeople().equals("2") ? image[2] : image[3],
                        Integer.parseInt(tableModel.getX()) - (tableModel.getPeople().equals("2") ? image[2].getWidth() / 2 : image[3].getWidth() / 2),
                        Integer.parseInt(tableModel.getY()) - (tableModel.getPeople().equals("2") ? image[2].getHeight() / 2 : image[3].getHeight() / 2),
                        imagePaint
                );
            }

            if(tableModel.getInfo().equals("0")) {
                canvas.drawBitmap(tableModel.getPeople().equals("2") ? image[0] : image[1],
                        Integer.parseInt(tableModel.getX()) - (tableModel.getPeople().equals("2") ? image[0].getWidth() / 2 : image[1].getWidth() / 2),
                        Integer.parseInt(tableModel.getY()) - (tableModel.getPeople().equals("2") ? image[0].getHeight() / 2 : image[1].getHeight() / 2),
                        imagePaint
                );
            }

            if(tableModel.getInfo().equals("2")) {
                canvas.drawBitmap(tableModel.getPeople().equals("2") ? image[4] : image[5],
                        Integer.parseInt(tableModel.getX()) - (tableModel.getPeople().equals("2") ? image[3].getWidth() / 2 : image[4].getWidth() / 2),
                        Integer.parseInt(tableModel.getY()) - (tableModel.getPeople().equals("2") ? image[3].getHeight() / 2 : image[4].getHeight() / 2),
                        imagePaint
                );

            }



        }

    }



    public boolean onTouch(MotionEvent event) {

        //Log.e("a", "onTouch: !!!!" );
        int action = event.getAction();
        for(TableModel tableModel : tables) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            //Log.e("X, y: ", event.getX() + ", " + event.getY() + "");
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    tableModel.onTuch(event);

                    break;

                case MotionEvent.ACTION_UP:
                    tableModel.onTuch(event);
                    break;

                case MotionEvent.ACTION_DOWN:
                    tableModel.onTuch(event);
                    break;
            }
        }
        return true;
    }


    public void setOnHomeMenuTouchInterface(HomeMenuFragment homeMenuFragment) {
        this.homeMenuTouchInterface = homeMenuFragment;
    }

}