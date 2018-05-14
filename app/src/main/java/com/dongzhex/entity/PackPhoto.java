package com.dongzhex.entity;

/**
 * Created by ASUS on 2018/5/3.
 */

public class PackPhoto {
    private String username;
    private String data;

    public PackPhoto(String username, String data) {
        this.username = username;
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
