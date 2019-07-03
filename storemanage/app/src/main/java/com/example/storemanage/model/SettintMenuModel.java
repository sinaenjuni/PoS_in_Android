package com.example.storemanage.model;

public class SettintMenuModel {
    private String menuID;

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }



    private String menuName;
    private String menuImageURL;
    private String menuPrice;
    private String menuOption;

    public SettintMenuModel() {
    }

    public SettintMenuModel(String menuImageURL, String menuName, String price, String option) {
        this.menuName = menuName;
        this.menuImageURL = menuImageURL;
        this.menuPrice = price;
        this.menuOption = option;
    }

    public SettintMenuModel(String menuID, String menuName, String menuImageURL, String menuPrice, String menuOption) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuImageURL = menuImageURL;
        this.menuPrice = menuPrice;
        this.menuOption = menuOption;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuImageURL() {
        return menuImageURL;
    }

    public void setMenuImageURL(String menuImageURL) {
        this.menuImageURL = menuImageURL;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuOption() {
        return menuOption;
    }

    public void setMenuOption(String menuOption) {
        this.menuOption = menuOption;
    }
}
