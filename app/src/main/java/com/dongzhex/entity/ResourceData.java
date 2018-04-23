package com.dongzhex.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by ASUS on 2018/4/13.
 */

public class ResourceData extends DataSupport {
    private String name;
    private String url;
    private int imageId = 0;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public ResourceData(String name, String url, int imageId) {
        this.name = name;
        this.url = url;
        this.imageId = imageId = 0;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResourceData(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
