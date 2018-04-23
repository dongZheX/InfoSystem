package com.dongzhex.someactivities.infosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class Personal_Center extends AppCompatActivity {
    private Spinner sex;
    private TextView username;
    private TextView Class_id;
    private EditText name;
    private EditText address;
    private EditText birth;
    private EditText QQ;
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        Toolbar toolbar= (Toolbar)findViewById(R.id.personal_toolbar);
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }
    private void onInitView(){
        String ssex,susername,sClass_id,sname,saddress,sbirth,sQQ,sphone;
        Intent intent = getIntent();
        //string由intent赋值
        sex = (Spinner)findViewById(R.id.sp_personal_sex);
        username = (TextView)findViewById(R.id.edit_personal_username);
        Class_id = (TextView)findViewById(R.id.edit_personal_Class_id);
        name = (EditText) findViewById(R.id.edit_personal_name);
        address = (EditText)findViewById(R.id.sp_personal_address);

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
