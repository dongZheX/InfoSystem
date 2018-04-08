package com.dongzhex.photoGetter;

/**
 * Created by ASUS on 2018/3/29.
 */

public class PackPhoto {
    private String ImageName;
    private String ImageUrl;

    public PackPhoto(String imageName, String imageUrl) {
        ImageName = imageName;
        ImageUrl = imageUrl;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
