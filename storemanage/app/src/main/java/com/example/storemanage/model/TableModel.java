package com.example.storemanage.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.animation.MotionSpec;
import android.util.Log;
import android.view.MotionEvent;

import com.example.storemanage.storeMain.Home.HomeMenuTouchInterface;
import com.example.storemanage.storeMain.Home.HomeTableCanvas;

public class TableModel {

    HomeMenuTouchInterface homeMenuTouchInterface;

    private String id;
    private String x;
    private String y;
    private String info;
    private String people;

    public TableModel() {
    }

    public void setHomeMenuTouchInterface(HomeMenuTouchInterface homeMenuTouchInterface){
        this.homeMenuTouchInterface = homeMenuTouchInterface;
    }

    public TableModel(String x, String y, String info, String people) {
        this.x = x;
        this.y = y;
        this.info = info;
        this.people = people;
    }


    public TableModel(String id, String x, String y, String info, String people) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.info = info;
        this.people = people;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "id='" + id + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", info='" + info + '\'' +
                ", people='" + people + '\'' +
                '}';
    }
    boolean bTouchCheck = false;
    public void onTuch(MotionEvent event){

        int action = event.getAction();

            int x = (int) event.getX();
            int y = (int) event.getY();
            //Log.e("X, y: ", event.getX() + ", " + event.getY() + "");
            switch (action) {
                case MotionEvent.ACTION_MOVE:

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

        if( (Integer.parseInt(this.x) - 150) < x && x < (Integer.parseInt(this.x) + 150) ) {
            if ((Integer.parseInt(this.y) - 150) < y && y < (Integer.parseInt(this.y) + 150)) {
                bTouchCheck = true;
                homeMenuTouchInterface.onMenuTouch(id);
                Log.e("터치!!!!!","@@@@@@@@@@@");
                Log.e("터치!!!!!","@@@@@@@@@@@" + this.id);
                //SharedVariable.vibrator.vibrate(new long[]{0,100},-1);
            }
        }

    }

}
