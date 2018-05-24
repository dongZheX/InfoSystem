package com.dongzhex.someactivities.infosystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.MyClass;
import com.dongzhex.webservice.getMyClassInfo;

public class MyClassActivity extends AppCompatActivity {
    private TextView Class_id;
    private TextView Class_name;
    private TextView Class_num;
    private MyClass myClass;
    private String classString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        Class_id = (TextView)findViewById(R.id.Class_id_myClass_text);
        Class_name = (TextView)findViewById(R.id.Class_name_myClass_text);
        Class_num = (TextView)findViewById(R.id.Class_num_myClass_text);
        Toolbar toolbar = (Toolbar)findViewById(R.id.myClass_toolbar);
        toolbar.setTitle("我的班级");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        classString = sharedPreferences1.getString("Class_id","");
        getMyClassInfo getMyClassInfos = new getMyClassInfo(myClass);
        getMyClassInfos.execute(classString);
        Class_name.setText(myClass.getClassName());
        Class_id.setText(myClass.getClass_id());
        Class_id.setText(myClass.getCount()+"");
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
