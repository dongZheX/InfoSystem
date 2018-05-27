package com.dongzhex.someactivities.infosystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dongzhex.NomalService.MD5;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.User;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.successListener;
import com.dongzhex.jsonService.JsonService;
import com.dongzhex.webservice.LoginService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox remember_pass;
    private Button login;
    private TextView find_back_pass;
    private String string_username="";
    private String string_password="";
    private Boolean isRemember;
    private SharedPreferences mainSetting;//主配置文件
    private CircleImageView circleImageView;//头像框
    private boolean SuccessFlag = false;//是否登陆成功
    private static final String TAG = "LoginActivity";
    private  User presentUser = null;
    private SharedPreferences share;
    private String trueName= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        //设置标题栏
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainSetting = getSharedPreferences("mainSetting",MODE_PRIVATE);
        if(mainSetting.getBoolean("isLogin",false)){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        //标题栏的home按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.system_zzz_keyboard_backspace);
        }
        username = (EditText)findViewById(R.id.Edit_username);
        password = (EditText)findViewById(R.id.Edit_password);
        login = (Button)findViewById(R.id.button_login);
        find_back_pass = (TextView)findViewById(R.id.find_back_pass);
        remember_pass = (CheckBox)findViewById(R.id.remember_pass);

        isRemember = mainSetting.getBoolean("isRemember",false);
        circleImageView = (CircleImageView)findViewById(R.id.loginPhoto);
        circleImageView.setImageResource(R.drawable.morentouxiang);

        if(isRemember){
            string_username = mainSetting.getString("username","");
            string_password =mainSetting.getString("password","");
            username.setText(string_username);
            password.setText(string_password);
            remember_pass.setChecked(true);
        }
        setLoginClick();
        if(checkNetwork()){

            Toast.makeText(this, "网络可用", Toast.LENGTH_SHORT).show();

        }
        share = getSharedPreferences("tempData",MODE_PRIVATE);
        SharedPreferences.Editor e = share.edit();
        e.clear();
        e.apply();

    }
    //菜单初始化
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //菜单点击时间
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_help_item:
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("登录帮助:");
                dialog.setMessage("本软件不提供登陆功能\n，请从管理员哪里获取账号密码进行登录.");
                dialog.setCancelable(true);
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            case android.R.id.home:
                LoginActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //login点击事件
    private void setLoginClick(){
        SuccessFlag = false;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                string_username = username.getText().toString();
                string_password = password.getText().toString();
                //输出信息控制
                if(!checkValidInfo()){
                    MessageBox.showMessageBox(LoginActivity.this,"警告","信息不合法",true).show();
                    return;
                }// end if checkValidInfo
                //保存子线程数据

                final SharedPreferences.Editor editors= share.edit();
                successListener myLisenter = new successListener() {
                    @Override
                    public void success(User x) {
                    }

                    @Override
                    public void success(String x) {
                        presentUser = JsonService.jsonToJavaBean(x,User.class);
                        Log.d(TAG, presentUser.toString());
                        Log.d(TAG, x);
                        if(presentUser!=null) {
                            trueName = presentUser.getUsername();
                        }
                        else{
                            //失败退出
                            MessageBox.showMessageBox(LoginActivity.this,"警告","网络错误",true);
                            return;
                        }
                        if (isRemember) {
                            //用户重新输入
                            if (!mainSetting.getString("username", "").equals(string_username) || !password.getText().toString().equals(mainSetting.getString("password", ""))) {

                                if (presentUser.textPass(MD5.getMD5(string_password)) && trueName.equals(string_username)) {
                                    //记住密码但是又换了信息的检验成功
                                    Toast.makeText(LoginActivity.this, "正确", Toast.LENGTH_SHORT).show();
                                    if (remember_pass.isChecked()) {
                                        SharedPreferences.Editor editor = mainSetting.edit();
                                        editor.putBoolean("isRemember", true);
                                        editor.putString("username", string_username);
                                        editor.putString("password", MD5.getMD5(string_password));//可能出现错误debug
                                        editor.apply();
                                    }
                                    SuccessFlag = true;
                                }
                            } else {

                                if (presentUser.textPass(string_password) && trueName.equals(string_username)) {
                                    Toast.makeText(LoginActivity.this, "正确", Toast.LENGTH_SHORT).show();
                                    if (!remember_pass.isChecked()) {
                                        SharedPreferences.Editor editor = mainSetting.edit();
                                        editor.putBoolean("isRemember", false);
                                        editor.putString("username", "");
                                        editor.putString("password", "");//可能出现错误debug
                                        editor.apply();
                                    }
                                    SuccessFlag = true;
                                }

                            }
                        }//end if isRemember
                        else{
                            Log.d(TAG, "onClick: ");
                            if (presentUser.textPass(MD5.getMD5(string_password)) && trueName.equals(string_username)) {
                                Toast.makeText(LoginActivity.this, "正确", Toast.LENGTH_SHORT).show();
                                if (remember_pass.isChecked()) {
                                    SharedPreferences.Editor editor = mainSetting.edit();
                                    editor.putBoolean("isRemember", true);
                                    editor.putString("username", string_username);
                                    editor.putString("password", MD5.getMD5(string_password));//可能出现错误debug
                                    editor.apply();
                                }
                                SuccessFlag = true;
                            }
                        }



                        if(!remember_pass.isChecked()){
                            //记住密码取消
                            SharedPreferences.Editor editor = mainSetting.edit();
                            editor.clear();
                            editor.apply();
                        }
                        if(SuccessFlag){
                            int firstLogin = presentUser.getFirstLogin();
                            String Class_id = presentUser.getClass_id();
                            int power = presentUser.getPower();
                            SharedPreferences presentUsers = getSharedPreferences("presentUser",MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = mainSetting.edit();
                            SharedPreferences.Editor editor = presentUsers.edit();
                            editor.putString("Class_id",Class_id);
                            editor.putInt("Power",power);
                            editor.putString("Username",trueName);
                            editor1.putString("presentUser",trueName);
                            editor1.putBoolean("isLogin",true);
                            editor.apply();
                            editor1.apply();
                            Intent intent = null;
                            //0第一次登陆，1不是第一次登陆
                            if(firstLogin == 0){
                                perfect_information_user.activityStart(LoginActivity.this,trueName);
                                LoginActivity.this.finish();
                            }

                            else{

                                intent = new Intent(LoginActivity.this,MainActivity.class);
                                intent.putExtra("args1",trueName);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                        }else{
                            MessageBox.showMessageBox(LoginActivity.this,"警告","密码或者用户名错误",true);
                            SharedPreferences.Editor editor = mainSetting.edit();
                            editor.clear();
                            editor.apply();
                            username.setText("");
                            password.setText("");
                            remember_pass.setChecked(false);
                            }
                        }



                    @Override
                    public void successInfo(List<Info> mlist) {
                    }

                    @Override
                    public void successUserX(List<UserX> mlist) {
                    }
                };
                LoginService loginS = new LoginService(myLisenter);
                if(!string_password.equals("")&&!string_password.equals(""))
                loginS.execute(string_username,string_password);
                //Log.d(TAG, presentUser.toString());
                /*String data;
                data= share.getString("data1","").replaceAll( "\\\\","");
                SharedPreferences.Editor e = share.edit();
                e.clear();
                e.apply();
                Log.d(TAG, data);
               // data = data.substring(1,data.length()-1);
                presentUser = JsonService.jsonToJavaBean(data,User.class);*/

                //得到presentsSer
                /*参数检验*/

                //检测是否输入合法


                    //记住密码设置

            }
        });
    }
    private boolean checkValidInfo(){
        string_username = username.getText().toString();
        string_password = password.getText().toString();
        boolean isValid = (string_username.length()>=6);
        boolean isNull = string_username.equals("")||string_password.equals("");
        return !isNull&&isValid;

    }
    private boolean checkNetwork() {

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connManager.getActiveNetworkInfo() != null) {

            return connManager.getActiveNetworkInfo().isAvailable();

        }

        return false;

    }


}
