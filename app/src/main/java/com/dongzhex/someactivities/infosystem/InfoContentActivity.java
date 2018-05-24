package com.dongzhex.someactivities.infosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dongzhex.entity.Info;

public class InfoContentActivity extends AppCompatActivity {
    private TextView Title_Text;
    private TextView Look_Num_Text;
    private TextView Author_Text;
    private TextView Time_Text;
    private TextView Content_Text;
    private Info info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_content);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_content_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Title_Text = (TextView)findViewById(R.id.info);
        Look_Num_Text = (TextView)findViewById(R.id.info_looked_num);
        Author_Text = (TextView)findViewById(R.id.info_person);
        Time_Text = (TextView)findViewById(R.id.info_date);
        Content_Text = (TextView)findViewById(R.id.notification_content_text);
        InitContent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_content_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.looked_num_item_wei:
            {   Intent intent = new Intent(this,UnLookInfoActivity.class);
                intent.putExtra("Info_id",info.getInfo_id());
                startActivity(intent);
                break;
            }

            case R.id.looked_num_item_yi:
                //未实现
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void InitContent(){
        Intent intent = getIntent();
        String Class_id = intent.getStringExtra("Class_id");
        String Info_id = intent.getStringExtra("Info_id");
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        int looked_num = intent.getIntExtra("looked_num",0);
        String time = intent.getStringExtra("time");
        String content = intent.getStringExtra("content");
        info = new Info(Class_id,Info_id,content,title,looked_num,time,author);
        Title_Text.setText(title);
        Author_Text.setText(author);
        Look_Num_Text.setText(looked_num+"");
        Time_Text.setText(time);
        Content_Text.setText(content);

    }
}
