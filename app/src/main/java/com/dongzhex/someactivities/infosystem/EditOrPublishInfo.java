package com.dongzhex.someactivities.infosystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dongzhex.entity.slStringInterface;
import com.dongzhex.webservice.publishInfoWebService;
import com.dongzhex.webservice.updateInfoWebService;

/**
 * Created by ASUS on 2018/5/26.
 */

public class EditOrPublishInfo extends AppCompatActivity {
    private EditText epi_title;
    private EditText epi_content;
    private Button epi_button;
    private String title;
    private String content;
    private String author;
    private SharedPreferences share;
    private String class_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_epi);
        epi_title = (EditText)findViewById(R.id.edp_title);
        epi_content = (EditText)findViewById(R.id.edp_content);
        epi_button = (Button)findViewById(R.id.button_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //获取上一个活动的数据
        Intent intent = getIntent();
        final int operateNum = intent.getIntExtra("operateNum",0);
        if(operateNum==0){
            //发布模式

        }else if(operateNum==1){
            //编辑模式
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            epi_title.setText(title);
            epi_content.setText(content);
            epi_button.setText("保存");
        }
        epi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (operateNum){
                    case 0:{
                        title = epi_title.getText().toString();
                        content = epi_title.getText().toString();
                        author = (share= getSharedPreferences("presentUser",MODE_PRIVATE)).getString("User_name","");
                        class_id = share.getString("Class_id","");
                        publishInfoWebService publishInfo = new publishInfoWebService();
                        publishInfo.execute(class_id,author,title,content);
                        break;
                    }


                    case 1: {
                        title = epi_title.getText().toString();
                        content = epi_title.getText().toString();
                        author = (share= getSharedPreferences("presentUser",MODE_PRIVATE)).getString("User_name","");
                        class_id = share.getString("Class_id","");
                        updateInfoWebService updateInfo = new updateInfoWebService(new slStringInterface() {
                            @Override
                            public void success(String s) {
                                String t = s.equals("success")?"修改成功":"修改失败";
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditOrPublishInfo.this);
                                builder.setTitle("提示");
                                builder.setCancelable(true);
                                builder.setMessage(t);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder.show();
                            }

                        });
                        updateInfo.execute(class_id,author,title,content);
                        break;
                    }


                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
