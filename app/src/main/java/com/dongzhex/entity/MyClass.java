package com.dongzhex.entity;

/**
 * Created by ASUS on 2018/5/20.
 */

public class MyClass {
    private String className;
    private String class_id;
    private int count;

    public MyClass(String className, String class_id, int count) {
        this.className = className;
        this.class_id = class_id;
        this.count = count;
    }

    public String getClassName() {

        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
