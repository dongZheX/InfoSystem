package com.dongzhex.NomalService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;

/**
 * Created by ASUS on 2018/4/1.
 */

public class BaseTool {
    //图片转字节
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100,baos);
        return baos.toByteArray();
    }
    //图片转字符串
    public static String Bitmap2String(Bitmap bitmap){
        return Base64.encodeToString(Bitmap2Bytes(bitmap),Base64.DEFAULT);
    }
    //字符串转图片
    public  static Bitmap String2Bitmap(String st){
        Bitmap bitmap = null;
        try{
            byte[] bitmapArray ;
            bitmapArray = Base64.decode(st,Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray,0,bitmapArray.length);
            return bitmap;
        }catch(Exception e){
            return null;
        }
    }
    //把bitmap转化成圆形//未了解
    public static Bitmap toRoundBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        if(width<height){
            r = width;
        }
        else{
            r = height;
        }
        Bitmap backgroundBm = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        //光滑
        p.setAntiAlias(true);
        RectF rect = new RectF(0,0,r,r);
        canvas.drawRoundRect(rect,r/2,r/2,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap,null,rect,p);
        return backgroundBm;

    }
    //验
    public static boolean isMobileNumber(String mobilenumber) {

        String val =  "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\\\d{8}$";
        boolean isPhone =Pattern.compile(val).matcher(val).matches();
        return isPhone;
    }
    public static boolean ChineseNameTest(String name) {

        String regex = "^[\\p{L} .'-]+$";

        boolean isName = Pattern.matches(regex, name);
        return isName;


    }

}
