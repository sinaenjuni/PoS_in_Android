package com.example.storemanage.storeMain.settings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.storemanage.R;
import com.example.storemanage.model.DrawTableModel;
import com.example.storemanage.model.SettintMenuModel;
import com.example.storemanage.model.TableModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DragSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    public static final long MILLIS_PER_FRAME = 18; // ~33fps // not guaranteed

    private Context mContext;
    private SurfaceHolder mHolder;

    private GameThread mGameThread;


    private int touchX, touchY;

    private Bitmap image[] = new Bitmap[2];

    private List<DrawTableModel> tables;



    //******************************************
    //Game Class 생성자 영역
    //******************************************


    public DragSurfaceView(Context context) {
        super(context);
        init();
    }

    public DragSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

      //  mContext = context;
        mHolder = holder;


        image[0] = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        image[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rect);

        image[0] = Bitmap.createScaledBitmap(image[0], 200, 200, true); //이미지 확대
        image[1] = Bitmap.createScaledBitmap(image[1], 400, 200, true); //이미지 확대

        //  backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background_image);
        //  backgroundImage = Bitmap.createScaledBitmap(backgroundImage, mWidth, mHeight, true); //이미지 확대

        tables = new ArrayList<>();
        //tables.add(new DrawTableModel(new Point(200, 200), "2", image[0].getHeight(), image[0].getWidth()));
        //tables.add(new DrawTableModel(new Point(400, 400), "4", image[1].getHeight(), image[1].getWidth()));

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

                    //Log.e("dataSet :" , settintMenuModel.getMenuID());
                    tables.add(new DrawTableModel(new Point(Integer.parseInt(tableModel.getX()), Integer.parseInt(tableModel.getY()))
                            ,tableModel.getPeople()
                            ,tableModel.getPeople().equals("2") ? 200 : 400
                            ,tableModel.getPeople().equals("2") ? 200 : 400));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mGameThread = new GameThread();

    }

    public void clearTable(){
        tables.clear();
    }

    public void addTwoTable(){
        tables.add(new DrawTableModel(new Point(200, 200), "2", image[0].getHeight(), image[0].getWidth()));
    }

    public void addForeTable(){
        tables.add(new DrawTableModel(new Point(400, 400), "4", image[1].getHeight(), image[1].getWidth()));
    }

    public void saveTables(){

        String uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("users").child(uid).child("tables").removeValue();


        for (DrawTableModel d : tables ) {
            FirebaseDatabase.getInstance().getReference()
                    .child("users").child(uid).child("tables").push()
                    .setValue(new TableModel(d.getPoint().x + "", d.getPoint().y + "", "0", d.getPeople()));
        }

    }


    //*********************************************************
    // SurfaceView 생성부분
    //***********************************************************

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mGameThread.setIsRun(true);
        mGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mGameThread.setIsRun(false);

        while(retry) {
            try {
                mGameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //******************************************
    //Draw 영역
    //******************************************

    public void drawArea(Canvas c){
        Paint paint = new Paint();
        Paint imagePaint = new Paint();
        imagePaint.setAntiAlias(true);

        for(DrawTableModel drawTableModel : tables){

            c.drawBitmap(drawTableModel.getPeople().equals("2") ? image[0]:image[1],
                    drawTableModel.getPoint().x - (drawTableModel.getPeople().equals("2") ? image[0].getWidth()/2 : image[1].getWidth()/2),
                    drawTableModel.getPoint().y - (drawTableModel.getPeople().equals("2") ? image[0].getHeight()/2 : image[1].getHeight()/2),
                    imagePaint
                 );

        }



    }



    //******************************************
    // onTouch 영역
    //******************************************


    public void setMyTouchEvent(MotionEvent event){

        touchX = (int) event.getX();
        touchY = (int) event.getY();

        for(DrawTableModel drawTableModel : tables){
            drawTableModel.onTouch(event);
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        synchronized (mHolder) {
            int action = event.getAction();


            switch (action) {
                case MotionEvent.ACTION_MOVE:

                    setMyTouchEvent(event);
                    break;

                case MotionEvent.ACTION_UP:
                    setMyTouchEvent(event);

                    break;

                case MotionEvent.ACTION_DOWN:
                    setMyTouchEvent(event);
                    break;
            }

            // return super.onTouchEvent(event);


        }
        return true;
    }

    //******************************************
    // Thread 영역
    //******************************************

    public class GameThread extends Thread{
        long now = 0, dt;
        long last = System.currentTimeMillis();
        Paint paint = new Paint();

        boolean isRun;

        public GameThread() {
            paint.setColor(new Color().WHITE);


        }

        public void setIsRun(boolean isRun){
            this.isRun = isRun;
        }

        @Override
        public void run() {
            Canvas c = null;
            while(isRun){
                c = mHolder.lockCanvas();
                try{
                    synchronized (mHolder) {

                        now = System.currentTimeMillis(); dt = (now - last);
                        while (dt < MILLIS_PER_FRAME) {
                            try{
                                Thread.sleep(1);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            now = System.currentTimeMillis();
                            dt = (now - last);
                        }


                        try{
                            c.drawRect(0, 0, StoreSettingActivity.mScreenWidth, StoreSettingActivity.mScreenHeight, paint);
                            drawArea(c);
                        }catch (Exception e){
                            e.printStackTrace();
                        }



                    }
                }finally {
                    if(c != null)
                        mHolder.unlockCanvasAndPost(c);
                }

            }

        }

    }//End of Thread
} //End of View
