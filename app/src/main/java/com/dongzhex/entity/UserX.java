package com.dongzhex.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by ASUS on 2018/4/12.
 */

public class UserX extends DataSupport implements Serializable{
    private String Username = "";       //用户名
    private String User_name = "";      //姓名
    private String Class_id = "";       //班级号
    private String User_sex = "";       //性别
    private String User_phone = "";     //联系方式


    private String User_address="";   //家庭地址
    private String User_QQ = "";        //QQ
    private String User_image = "";     //照片
    private String birth = "";
    public UserX() {

    }
    public UserX(UserX userx) {
        Username = userx.getUsername();
        User_name = userx.getUser_name();
        Class_id = userx.getClass_id();
        User_sex = userx.getUser_sex();
        User_phone = userx.getUser_phone();
        User_address = userx.getUser_address();
        User_QQ = userx.getUser_QQ();
        User_image = userx.getUser_image();
        birth = userx.getBirth();
    }
    public String getBirth() {
        return birth;
    }


    @Override
    public String toString() {
        return "UserX{" +
                "Username='" + Username + '\'' +
                ", User_name='" + User_name + '\'' +
                ", Class_id='" + Class_id + '\'' +
                ", User_sex='" + User_sex + '\'' +
                ", User_phone='" + User_phone + '\'' +
                ", User_address='" + User_address + '\'' +
                ", User_QQ='" + User_QQ + '\'' +
                ", User_image='" + User_image + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public UserX(String username, String user_name, String class_id, String user_sex, String user_phone, String birth) {
        Username = username;
        User_name = user_name;
        Class_id = class_id;
        User_sex = user_sex;
        User_phone = user_phone;
        this.birth = birth;
    }

    public UserX(String username, String user_name, String class_id, String user_sex, String user_phone, String user_address, String user_QQ, String user_image, String birth) {
        Username = username;
        User_name = user_name;
        Class_id = class_id;
        User_sex = user_sex;
        User_phone = user_phone;
        User_address = user_address;
        User_QQ = user_QQ;
        User_image = user_image;
        this.birth = birth;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getClass_id() {
        return Class_id;
    }

    public void setClass_id(String class_id) {
        Class_id = class_id;
    }

    public String getUser_sex() {
        return User_sex;
    }

    public void setUser_sex(String user_sex) {
        User_sex = user_sex;
    }

    public String getUser_phone() {
        return User_phone;
    }

    public void setUser_phone(String user_phone) {
        User_phone = user_phone;
    }

    public String getUser_address() {
        return User_address;
    }

    public void setUser_address(String user_address) {
        User_address = user_address;
    }

    public String getUser_QQ() {
        return User_QQ;
    }

    public void setUser_QQ(String user_QQ) {
        User_QQ = user_QQ;
    }

    public String getUser_image() {
        return User_image;
    }

    public void setUser_image(String user_image) {
        User_image = user_image;
    }

}
