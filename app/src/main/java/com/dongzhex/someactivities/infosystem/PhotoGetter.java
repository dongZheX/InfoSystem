package com.dongzhex.someactivities.infosystem;


import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dongzhex.NomalService.Myapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


/*
未测试，一个需要取得图片的基础类
 */
public class PhotoGetter extends AppCompatActivity {



    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    protected ImageView image[];

    protected Bitmap bitmap[];
    protected CircleImageView circleImageView[];

    protected int counT;

    protected String Path;
    String userName;
    Uri imageUri;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        image = new ImageView[1000];

        bitmap = new Bitmap[1000];
        circleImageView = new CircleImageView[1000];

    }

    protected void takePhoto(int count) {

        counT = count;



        File imageFile = new File(getExternalCacheDir(), "image_take");



        try {

            if (imageFile.exists()) {

                imageFile.delete();

            } else {

                imageFile.createNewFile();

            }



        } catch (IOException e) {

            Toast.makeText(PhotoGetter.this, "系统错误1", Toast.LENGTH_SHORT).show();

        }

        if (Build.VERSION.SDK_INT >= 24) {

           // imageUri = FileProvider.getUriForFile(PhotoGetter.this, "com.dongzhex.someactivities.infosystem.perfect_information_user", imageFile);

            //可以想这是一个为imageFile开辟的提供器共享给外界，外界可以利用这个提供器来对imageFIle进行操作

        } else {

            imageUri = Uri.fromFile(imageFile);

        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra("SIGN_PHOTO",count);

        startActivityForResult(intent, TAKE_PHOTO);

    }

    public void choosePhoto(int count,String username) {

        //SD卡读写能力

        counT = count;
        userName = username;
        if (ContextCompat.checkSelfPermission(PhotoGetter.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(PhotoGetter.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {

            openAlbum(count);

        }

    }



    private void openAlbum(int count){

        Intent intent = new Intent("android.intent.action.GET_CONTENT");

        intent.setType("image/*");//读取相册的意图

        intent.putExtra("SIGN_IMAGE",count);

        startActivityForResult(intent,CHOOSE_PHOTO);

    }



    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case 1:if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {

                openAlbum(counT);

            }

            else{

                Toast.makeText(this,"您拒绝了",Toast.LENGTH_SHORT).show();

            }

        }





    }







    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case TAKE_PHOTO:

                if(resultCode == RESULT_OK){





                    try {

                        int count =data.getIntExtra("SIGN_IMAGE",999);

                        bitmap[count] = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    }



                }

                break;

            case CHOOSE_PHOTO:

                if(resultCode==RESULT_OK) {



                    if (Build.VERSION.SDK_INT >= 19) {

                        handleImageOnKitKat(data);

                    } else {

                        handleImageBeforeKitKat(data);

                    }

                    break;

                }

            default:break;

        }





    }

    protected void handleImageOnKitKat(Intent data){

        //使用相机是对本程序的一张图片进行了编辑得到的uri是可以直接使用的uri，但是用其他程序获取的照片的intent中获取uri是需要

        //解析的，这可能是一种安全机制，而且在调用相机的时候也没有传入uri用来赋值

        String imagePath = null;

        Uri uri = data.getData();//可能给的并不是图片的真实uri不用于拍照

        if(DocumentsContract.isDocumentUri(this,uri)){

            String id= DocumentsContract.getDocumentId(uri);

            if(uri.getAuthority().equals("com.android.providers.media.documents")){

                id = id.split(":")[1];

                String selection = MediaStore.Images.Media._ID+"="+id;

                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);//多媒体数据库

            }

            else if(uri.getAuthority().equals("com.android.providers.downloads.documents")){

                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));

                imagePath = getImagePath(contentUri,null);

            }

        }

        else if ("content".equalsIgnoreCase(uri.getScheme())){

            imagePath = getImagePath(uri,null);

        }

        else if("file".equalsIgnoreCase(uri.getScheme())){

            imagePath = uri.getPath();

        }

        displayImage(imagePath);

    }

    protected void handleImageBeforeKitKat(Intent data){

        Uri uri = data.getData();

        String imagePath = getImagePath(uri,null);



        displayImage(imagePath);

    }

    protected String getImagePath(Uri uri,String selection){

        String path = null;

        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);

        if(cursor!=null){

            if(cursor.moveToFirst()){

                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }

            cursor.close();

        }

        return path;

    }

    protected void displayImage(String path){

        Path = path;

        Bitmap bitmaps = BitmapFactory.decodeFile(path);

        System.out.println(path);

        Glide.with(Myapplication.getRealContext()).load(path).into(circleImageView[counT]);
        bitmap[counT] = bitmaps;



    }

}