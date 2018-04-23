package com.dongzhex.someactivities.infosystem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

public class ResetPassword extends AppCompatActivity {
    private EditText old_pass;
    private EditText new_pass;
    private Button reset_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        old_pass = (EditText)findViewById(R.id.old_pass_edit);
        new_pass = (EditText)findViewById(R.id.new_pass_edit);
        reset_button = (Button)findViewById(R.id.button_resetPass);
        Toolbar toolbar = (Toolbar)findViewById(R.id.reset_pass_toolbar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
