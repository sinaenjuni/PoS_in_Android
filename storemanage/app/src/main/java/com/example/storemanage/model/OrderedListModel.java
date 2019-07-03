package com.example.storemanage.model;

public class OrderedListModel {

    private String OrderListId;
    private String OrderListName;
    private String OrderListPrice;

    public OrderedListModel() {
    }

    public OrderedListModel(String orderListName, String orderListPrice) {
        OrderListName = orderListName;
        OrderListPrice = orderListPrice;
    }

    public OrderedListModel(String orderListId, String orderListName, String orderListPrice) {
        OrderListId = orderListId;
        OrderListName = orderListName;
        OrderListPrice = orderListPrice;
    }

    public String getOrderListId() {
        return OrderListId;
    }

    public void setOrderListId(String orderListId) {
        OrderListId = orderListId;
    }

    public String getOrderListName() {
        return OrderListName;
    }

    public void setOrderListName(String orderListName) {
        OrderListName = orderListName;
    }

    public String getOrderListPrice() {
        return OrderListPrice;
    }

    public void setOrderListPrice(String orderListPrice) {
        OrderListPrice = orderListPrice;
    }
}
