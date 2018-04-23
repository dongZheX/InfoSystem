package com.dongzhex.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ASUS on 2018/4/12.
 */

public class Info extends DataSupport{

    private String Class_id=null;         //班级号
    private String Info_id=null;         //通知编号
    private String Info_content=null;    //通知内容1
    private String Info_title=null;     //通知标题1
    private int Looked_num=0;        //通知查看数1
    private String time=null;          //发布时间1
    private String Info_author=null;    //发布者
    private String image_path=null;     //图片路径

    public Info(String class_id, String info_id, String info_content, String info_title, int looked_num, String time, String info_author, String image_path) {
        Class_id = class_id;
        Info_id = info_id;
        Info_content = info_content;
        Info_title = info_title;
        Looked_num = looked_num;
        this.time = time;
        Info_author = info_author;
        this.image_path = image_path;
    }

    public Info(String info_content, String info_title) {
        Info_content = info_content;
        Info_title = info_title;
    }


    public Info(String info_id, String info_content, String info_title, String info_author, String image_path) {
        Info_id = info_id;
        Info_content = info_content;
        Info_title = info_title;
        Info_author = info_author;
        this.image_path = image_path;
    }

    public Info(String class_id, String info_id, String info_content, String info_title, int looked_num, String time, String info_author) {
        Class_id = class_id;
        Info_id = info_id;
        Info_content = info_content;
        Info_title = info_title;
        Looked_num = looked_num;
        this.time = time;
        Info_author = info_author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Info info = (Info) o;

        return Info_id.equals(info.Info_id);

    }

    @Override
    public int hashCode() {
        return Info_id.hashCode();
    }

    public String getClass_id() {
        return Class_id;
    }

    public void setClass_id(String class_id) {
        Class_id = class_id;
    }

    public String getInfo_id() {
        return Info_id;
    }

    public void setInfo_id(String info_id) {
        Info_id = info_id;
    }

    public String getInfo_content() {
        return Info_content;
    }

    public void setInfo_content(String info_content) {
        Info_content = info_content;
    }

    public String getInfo_title() {
        return Info_title;
    }

    public void setInfo_title(String info_title) {
        Info_title = info_title;
    }

    public int getLooked_num() {
        return Looked_num;
    }

    public void setLooked_num(int looked_num) {
        Looked_num = looked_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo_author() {
        return Info_author;
    }

    public void setInfo_author(String info_author) {
        Info_author = info_author;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
