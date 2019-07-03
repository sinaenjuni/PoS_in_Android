package com.example.storemanage.model;

public class EventLogModel {

    private String id;
    private String type;
    private String msg;
    private String time;

    public EventLogModel() {
    }

    public EventLogModel(String type, String msg, String time) {
        this.type = type;
        this.msg = msg;
        this.time = time;
    }

    public EventLogModel(String id, String type, String msg, String time) {
        this.id = id;
        this.type = type;
        this.msg = msg;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
