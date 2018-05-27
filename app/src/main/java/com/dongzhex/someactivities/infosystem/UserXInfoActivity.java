package com.dongzhex.someactivities.infosystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;

/*
没有圆形图片框
 */
public class UserXInfoActivity extends AppCompatActivity {

    private EditText name;
    private EditText sex;
    private EditText phone;
    private EditText address;
    private EditText QQ;
    private ImageView user_iamge;
    private EditText birth;
    private UserX user;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userx_info);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_userx_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("          您要的班级通讯录:");
        }
        initUserX();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.user_info_call_button);
        setButton();

    }
    public void initUserX(){
        //获取上层活动信息
        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        String User_name = intent.getStringExtra("User_name");
        String Class_id = intent.getStringExtra("Class_id");
        String User_Sex = intent.getStringExtra("User_sex");

        String User_phone = intent.getStringExtra("USer_phone");
        String User_QQ = intent.getStringExtra("User_QQ");
        String User_address = intent.getStringExtra("User_address");
        String User_image = intent.getStringExtra("User_image");
        String  births  = intent.getStringExtra("User_birth");
        //初始化控件
        name = (EditText)findViewById(R.id.userx_info_name);
        name.setText(User_name);
        sex= (EditText)findViewById(R.id.userx_info_sex);
        sex.setText(User_Sex);
        phone = (EditText)findViewById(R.id.userx_info_phone);
        phone.setText(User_phone);
        address= (EditText)findViewById(R.id.userx_info_address);
        address.setText(User_address);
        QQ = (EditText)findViewById(R.id.userx_info_QQ);
        QQ.setText(User_QQ);
        birth = (EditText)findViewById(R.id.userx_info_birth);
        birth.setText(births);
        user = new UserX(username,User_name,Class_id,User_Sex,User_phone,User_QQ,User_address,User_image,births);
        user_iamge = (ImageView)findViewById(R.id.userx_info_image);
        //加载图片
        if(User_image!=null){
            Glide.with(this).load(NetUnit.URL+"/InfoSystem"+User_image).error(R.drawable.morentouxiang).into(user_iamge);
        }
    }
    public void setButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+user.getUser_phone()));
                if(ContextCompat.checkSelfPermission(Myapplication.getRealContext(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    Myapplication.getRealContext().startActivity(intent);
                }
                else{
                    Toast.makeText(Myapplication.getRealContext(), "您没有相应的权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
