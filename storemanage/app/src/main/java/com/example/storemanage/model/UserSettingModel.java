package com.example.storemanage.model;

public class UserSettingModel {

    private String id;
    private String imageUrl;
    private String name;
    private String phone;

    public UserSettingModel() {
    }

    public UserSettingModel(String imageUrl, String name, String phone) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.phone = phone;
    }

    public UserSettingModel(String id, String imageUrl, String name, String phone) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
