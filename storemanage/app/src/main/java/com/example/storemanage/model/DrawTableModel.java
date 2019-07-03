package com.example.storemanage.model;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class DrawTableModel {
    private Point point;
    private String people;
    private int centerH;
    private int centerW;
    private boolean bTouchCheck;
    private int height, widht;

    public DrawTableModel() {
    }

    public DrawTableModel(Point point, String people, int height, int widht) {
        this.point = point;
        this.people = people;
        this.height = height;
        this.widht = widht;
        this.centerH = height/2;
        this.centerW = widht/2;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public int getCenterH() {
        return centerH;
    }

    public void setCenterH(int centerH) {
        this.centerH = centerH;
    }

    public int getCenterW() {
        return centerW;
    }

    public void setCenterW(int centerW) {
        this.centerW = centerW;
    }

    public boolean isbTouchCheck() {
        return bTouchCheck;
    }

    public void setbTouchCheck(boolean bTouchCheck) {
        this.bTouchCheck = bTouchCheck;
    }

    public void setPoint(int x, int y){

       // Log.e("@@@@@@: ", centerW +", "+ centerH +", "+widht +", "+height);
       // Log.e("@@@@@@@@@@@: ", ( centerW) +", "+ (widht - centerW) +"," +
               // " "+(centerH) +", "+(height - centerH));

       // Log.e("X, y: ", x + ", " + y + "");
        //Log.e("터치!!!!!","!!!!!");
       // if((0+ centerW) <= x && x <= (widht - centerW)){
         //   if ((0 + centerH) <= y && y <= ( height - centerH)) {
       // if( (point.x - centerW) < x && x < (point.x + centerW) ) {
         //   if ((point.y - centerH) < y && y < (point.y + centerH)) {
                Log.e("터치!!!!!", "?????");
                this.point.x = x;
                this.point.y = y;
         //   }
      //  }
          //  }
      //  }
    }


    public void onTouch(MotionEvent event){
        int action = event.getAction();

        int x = (int)event.getX();
        int y = (int)event.getY();
        //Log.e("X, y: ", event.getX() + ", " + event.getY() + "");
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if(bTouchCheck) {
                    setPoint(x, y);
                }
                break;

            case MotionEvent.ACTION_UP:
                bTouchCheck = false;
                break;

            case MotionEvent.ACTION_DOWN:
                checkOnTouch(x, y);
                break;
        }

    }

    public void checkOnTouch(int x, int y){

            if( (point.x - centerW) < x && x < (point.x + centerW) ) {
                if ((point.y - centerH) < y && y < (point.y + centerH)) {
                    bTouchCheck = true;
                    // Log.e("터치!!!!!","@@@@@@@@@@@");
                    //SharedVariable.vibrator.vibrate(new long[]{0,100},-1);
                }
            }

    }

}
