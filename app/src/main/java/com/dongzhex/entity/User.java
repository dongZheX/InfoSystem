package com.dongzhex.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ASUS on 2018/4/12.
 */

public class User extends DataSupport{
    private String username;
    private String password;
    private int power;
    private String Class_id;

    public User(String username, String password, int power, String class_id) {
        this.username = username;
        this.password = password;
        this.power = power;
        Class_id = class_id;
    }

    public String getClass_id() {

        return Class_id;
    }

    public void setClass_id(String class_id) {
        Class_id = class_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
