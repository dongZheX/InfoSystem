package com.dongzhex.someactivities.infosystem;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.webservice.ResetPassWebService;

public class ResetPassword extends AppCompatActivity {
    private EditText old_pass;
    private EditText new_pass;
    private Button reset_button;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        old_pass = (EditText)findViewById(R.id.old_pass_edit);
        new_pass = (EditText)findViewById(R.id.new_pass_edit);
        reset_button = (Button)findViewById(R.id.button_resetPass);
        Toolbar toolbar = (Toolbar)findViewById(R.id.reset_pass_toolbar);
        toolbar.setTitle("修改密码");
        //标题栏
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //参数获取
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        username = sharedPreferences1.getString("Username","");
        //设置按钮点击事件
        setButton();

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

    public void setButton(){
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = old_pass.getText().toString();
                String newPass = new_pass.getText().toString();
                if(oldPass.equals(newPass)){
                    MessageBox.showMessageBox(Myapplication.getRealContext(),"警告","新密码和旧密码一致",true);
                }
                else if(textPass(oldPass)&&textPass(newPass)){
                    ResetPassWebService resetPassWebService = new ResetPassWebService(ResetPassword.this);
                    resetPassWebService.execute(username,oldPass,newPass);
                 }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
                    builder.setTitle("警告");
                    builder.setMessage("您输入的不合法，请重新输入");
                    builder.setCancelable(true);
                    builder.show();
                }
            }
        });
    }
    public boolean textPass(String pass){
        if(pass!=null){
            if(pass.length()<6) {
                return false;
            }
            else return true;
        }
        else
            return false;

    }
}
