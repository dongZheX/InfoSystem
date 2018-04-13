package com.dongzhex.someactivities.infosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class ViewResource extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resource);
        WebView webView = (WebView)findViewById(R.id.webView_Resource);
        TextView textView = (TextView)findViewById(R.id.viewResource_toolbar_title) ;//toolbar标题
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");
        String title = intent.getStringExtra("title");
        textView.setText(title);
        URL urls = null;
        try {
            urls = new URL(url);
            webView.loadUrl(urls.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //返回按钮
        Toolbar toolBar = (Toolbar)findViewById(R.id.viewResource_toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.system_zzz_keyboard_backspace);
        }
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
