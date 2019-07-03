package com.example.storemanage.model;

public class OrderItemModel {

    private String menuOrderId;
    private String menuOrderName;
    private String menuOrderPrice;

    public OrderItemModel() {
    }

    public OrderItemModel(String menuOrderName, String menuOrderPrice) {
        this.menuOrderName = menuOrderName;
        this.menuOrderPrice = menuOrderPrice;
    }

    public OrderItemModel(String menuOrderId, String menuOrderName, String menuOrderPrice) {
        this.menuOrderId = menuOrderId;
        this.menuOrderName = menuOrderName;
        this.menuOrderPrice = menuOrderPrice;
    }

    public String getMenuOrderId() {
        return menuOrderId;
    }

    public void setMenuOrderId(String menuOrderId) {
        this.menuOrderId = menuOrderId;
    }

    public String getMenuOrderName() {
        return menuOrderName;
    }

    public void setMenuOrderName(String menuOrderName) {
        this.menuOrderName = menuOrderName;
    }

    public String getMenuOrderPrice() {
        return menuOrderPrice;
    }

    public void setMenuOrderPrice(String menuOrderPrice) {
        this.menuOrderPrice = menuOrderPrice;
    }
}
