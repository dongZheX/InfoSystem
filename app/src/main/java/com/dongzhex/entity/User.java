package com.dongzhex.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ASUS on 2018/4/12.
 */

public class User extends DataSupport{
    private String username;
    private String password;
    private int power;

    public int getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(int firstLogin) {
        this.firstLogin = firstLogin;
    }

    private String Class_id;
    private int firstLogin;

    public User(String username, String password, int power, String class_id,int firstLogin) {
        this.username = username;
        this.password = password;
        this.power = power;
        Class_id = class_id;
        this.firstLogin = firstLogin;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        power = 0;
        Class_id = null;
    }
    public User (User s){
        this.power=s.getPower();
        this.username =s.getUsername();
        this.password = s.getPassword();

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

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", power=" + power +
                ", Class_id='" + Class_id + '\'' +
                ", firstLogin=" + firstLogin +
                '}';
    }

    public boolean textPass(String data){
       // Log.d("text", password);
        return data.equals(password);

    }
}
