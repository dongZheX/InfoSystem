package com.dongzhex.NomalService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ASUS on 2018/4/28.
 */

public class MD5 {
    public static String getMD5(String content){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(content.getBytes());
            return getHashString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getHashString(MessageDigest digest){
        StringBuilder builder = new StringBuilder();
        for(byte b:digest.digest()){
            builder.append(Integer.toHexString((b>>4)&0xf));
            builder.append(Integer.toHexString(b&0xf));

        }
        return builder.toString();
    }
}
