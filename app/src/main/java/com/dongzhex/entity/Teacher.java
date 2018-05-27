package com.dongzhex.entity;

/**
 * Created by ASUS on 2018/5/26.
 */

public class Teacher {
    private String teacher_name = "";
    private String teach_course = "";
    private String taach_phone = "";
    private String Class_id = "";

    public Teacher(String teacher_name, String teach_course, String taach_phone, String class_id) {
        this.teacher_name = teacher_name;
        this.teach_course = teach_course;
        this.taach_phone = taach_phone;
        Class_id = class_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeach_course() {
        return teach_course;
    }

    public void setTeach_course(String teach_course) {
        this.teach_course = teach_course;
    }

    public String getTaach_phone() {
        return taach_phone;
    }

    public void setTaach_phone(String taach_phone) {
        this.taach_phone = taach_phone;
    }

    public String getClass_id() {
        return Class_id;
    }

    public void setClass_id(String class_id) {
        Class_id = class_id;
    }
}
