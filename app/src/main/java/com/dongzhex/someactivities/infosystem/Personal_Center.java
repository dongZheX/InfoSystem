package com.dongzhex.someactivities.infosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
import com.dongzhex.webservice.PerfectInfoUserX;
import com.dongzhex.webservice.UploadBitmap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Personal_Center extends PhotoGetter {
    private Spinner sex;
    private TextView username;
    private TextView Class_id;
    private EditText name;
    private EditText address;
    private EditText year,mon,day;
    private EditText QQ;
    private EditText phone;
    private CircleImageView image;
    private UserX user,temp;
    private Button save,edit,personal_choose_photo;
    private static final String TAG = "Personal_Center";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        Toolbar toolbar= (Toolbar)findViewById(R.id.personal_toolbar);
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        onInitView();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        user = (UserX)getIntent().getSerializableExtra("userx");
        setButton();
    }
    private void setButton(){
        save = (Button)findViewById(R.id.personal_save_button);
        edit = (Button)findViewById(R.id.personal_edit_button);
        personal_choose_photo = (Button)findViewById(R.id.personal_choose_photo);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                String ssex,susername,sClass_id,sname,saddress,sbirth,sQQ,sphone;
                ssex = sex.getSelectedItem().toString();
                susername = username.getText().toString();
                sClass_id = Class_id.getText().toString();
                sname = name.getText().toString();
                saddress = address.getText().toString();
                sbirth = year.getText().toString()+"/"+mon.getText().toString()+"/"+day.getText().toString();
                sQQ = QQ.getText().toString();
                sphone = phone.getText().toString();
                temp =new UserX(susername,sname,sClass_id,ssex,sphone,saddress,sQQ,"",sbirth);
                try {
                    PerfectInfoUserX service = new PerfectInfoUserX();
                    service.execute(temp);
                }catch (Exception e){
                    MessageBox.showMessageBox(Personal_Center.this,"bug提示","未调试的错误002",false);
                }
                MessageBox.showMessageBox(Personal_Center.this,"提示","保存成功",false);
                sex.setFocusable(false);
                name.setFocusable(false);
                address.setFocusable(false);
                QQ.setFocusable(false);
                phone.setFocusable(false);
                year.setFocusable(false);
                mon.setFocusable(false);
                day.setFocusable(false);
                personal_choose_photo.setFocusable(false);
                edit.setText("编辑");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许编辑
                if(edit.getText().toString().equals("编辑")) {
                    sex.setFocusable(true);
                    name.setFocusable(true);
                    address.setFocusable(true);
                    QQ.setFocusable(true);
                    phone.setFocusable(true);
                    year.setFocusable(true);
                    mon.setFocusable(true);
                    day.setFocusable(true);
                    personal_choose_photo.setFocusable(true);
                    edit.setText("取消编辑");
                }
                else{
                    sex.setFocusable(false);
                    name.setFocusable(false);
                    address.setFocusable(false);
                    QQ.setFocusable(false);
                    phone.setFocusable(false);
                    year.setFocusable(false);
                    mon.setFocusable(false);
                    day.setFocusable(false);
                    personal_choose_photo.setFocusable(false);
                    edit.setText("编辑");
                }
            }
        });
        personal_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePhoto(0);
                    UploadBitmap uploadBitmap = new UploadBitmap(username.getText().toString());
                    uploadBitmap.execute(bitmap[0]);
                }catch(Exception ex){
                    MessageBox.showMessageBox(Personal_Center.this,"bug提示","未调试的错误",false);
                }
            }
        });
    }
    private void onInitView(){
        String ssex,susername,sClass_id,sname,saddress,sbirth,sQQ,sphone;
        String birtht[] = new String[3];
        Intent intent = getIntent();
        //string由intent赋值
        ssex = user.getUser_sex();
        susername = user.getUsername();
        sClass_id = user.getClass_id();
        sname = user.getUser_name();
        saddress = user.getUser_address();
        sbirth = user.getBirth();
        sQQ = user.getUser_QQ();
        sphone = user.getUser_phone();
        if(user.getUser_image()!=null&&user.getUser_image()!="")
        Glide.with(this)
        . load(NetUnit.URL+"/InfoSystem"+user.getUser_image()).error(R.drawable.morentouxiang);
        sex = (Spinner)findViewById(R.id.sp_personal_sex);
        username = (TextView)findViewById(R.id.edit_personal_username);
        Class_id = (TextView)findViewById(R.id.edit_personal_Class_id);
        name = (EditText) findViewById(R.id.edit_personal_name);
        address = (EditText)findViewById(R.id.sp_personal_address);
        year = (EditText)findViewById(R.id.sp_personal_year);
        mon = (EditText)findViewById(R.id.sp_personal_month);
        day = (EditText)findViewById(R.id.sp_personal_day);
        phone = (EditText)findViewById(R.id.sp_personal_phone);
        QQ = (EditText)findViewById(R.id.edit_personal_QQ);
        try {
            if (ssex.equals("男"))
                sex.setSelection(1);
            else
                sex.setSelection(2);//可能出错
        }catch(Exception e){
            Log.d(TAG, "spinner出错");
        }
        username.setText(susername);
        Class_id.setText(sClass_id);
        name.setText(sname);
        address.setText(saddress);
        //格式处理
        birtht = sbirth.split("/");
        year.setText(birtht[0]);
        mon.setText(birtht[1]);
        day.setText(birtht[2]);
        QQ.setText(sQQ);
        phone.setText(sphone);
        address.setText(saddress);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                 this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
