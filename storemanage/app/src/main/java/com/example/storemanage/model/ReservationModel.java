package com.example.storemanage.model;

public class ReservationModel {

    private String ID;
    private String editMan;
    private String editMenu;
    private String editName;
    private String editPhone;
    private String editTime;

    public ReservationModel() {
    }

    public ReservationModel(String editMan, String editMenu, String editName, String editPhone, String editTime) {
        this.editMan = editMan;
        this.editMenu = editMenu;
        this.editName = editName;
        this.editPhone = editPhone;
        this.editTime = editTime;
    }

    public ReservationModel(String ID, String editMan, String editMenu, String editName, String editPhone, String editTime) {
        this.ID = ID;
        this.editMan = editMan;
        this.editMenu = editMenu;
        this.editName = editName;
        this.editPhone = editPhone;
        this.editTime = editTime;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEditMan() {
        return editMan;
    }

    public void setEditMan(String editMan) {
        this.editMan = editMan;
    }

    public String getEditMenu() {
        return editMenu;
    }

    public void setEditMenu(String editMenu) {
        this.editMenu = editMenu;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditPhone() {
        return editPhone;
    }

    public void setEditPhone(String editPhone) {
        this.editPhone = editPhone;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }
}
