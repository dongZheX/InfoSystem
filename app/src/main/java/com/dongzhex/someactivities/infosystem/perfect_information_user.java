package com.dongzhex.someactivities.infosystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.entity.UserX;
import com.dongzhex.webservice.PerfectInfoUserX;
import com.dongzhex.webservice.UploadBitmap;

import de.hdodenhof.circleimageview.CircleImageView;

public class perfect_information_user extends PhotoGetter {
    private String username;
    private EditText perfect_name;//姓名输入
    private EditText perfect_phone;//电话输入
    private RadioButton man;//单选
    private RadioButton woman;//单选
    private CircleImageView imageViewT;
    private Button choose_photo_button;
    private Button set_confirm;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_information_user);
        Toolbar toolbar_perfect = (Toolbar)findViewById(R.id.perfect_toolbar);
        setSupportActionBar(toolbar_perfect);
         perfect_name = (EditText)findViewById(R.id.perfect_name);
         perfect_phone = (EditText)findViewById(R.id.perfect_phone);
         imageViewT = (CircleImageView)findViewById(R.id.perfect_photo);
         choose_photo_button = (Button)findViewById(R.id.perfect_button_photo);
         man  = (RadioButton) findViewById(R.id.radio_button_man);
         woman = (RadioButton)findViewById(R.id.radio_button_woman);
        username = getIntent().getStringExtra("args1");
         set_confirm = (Button)findViewById(R.id.perfect_confirm);
         setButton();
    }
    //设置点击事件
    private void setButton(){
        circleImageView[0] = imageViewT;
        choose_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto(0,username);

            }
        });
        set_confirm.setOnClickListener(new View.OnClickListener() {

            final String sex = man.isChecked()?"男":"女";
            final String phone = perfect_phone.getText().toString();
            final String name = perfect_name.getText().toString();
            final boolean isPhone = BaseTool.isMobileNumber(phone);
            final boolean isName = BaseTool.ChineseNameTest(name);
            @Override
            public void onClick(View v) {

                if(!isName&&isPhone){
                    MessageBox.showMessageBox(perfect_information_user.this,"ERROR","电话和姓名都不合法",true).show();
                }
                else if(!isName){
                    MessageBox.showMessageBox(perfect_information_user.this,"ERROR","姓名不合法",true).show();
                }
                else if(!isPhone){
                    MessageBox.showMessageBox(perfect_information_user.this,"ERROR","电话号码不合法",true).show();
                }
                else{
                    if(bitmap[0]!=null) {
                        UploadBitmap uploadBitmap = new UploadBitmap(username);
                        uploadBitmap.execute(bitmap[0]);
                    }
                    else{
                      MessageBox.showMessageBox(perfect_information_user.this,"提示","请选择头像",true).show();
                        return;
                    }
                    UserX updateUser = new UserX();
                    updateUser.setUsername(username);
                    updateUser.setUser_sex(sex);
                    updateUser.setUser_phone(phone);
                    updateUser.setUser_name(name);
                    PerfectInfoUserX perfectInfoUserX = new PerfectInfoUserX();
                    perfectInfoUserX.execute(updateUser);

                }
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perfect_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.perfect_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(perfect_information_user.this);
                builder.setTitle("帮助");
                builder.setCancelable(true);
                builder.setMessage("应管理员要求：\n您必须完善下列信息，如有问题请联系：15650111502");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    //启动
    public static void activityStart(Context context,String...args){
        Intent intent = new Intent(context,perfect_information_user.class);
        for(int i = 0;i<args.length;i++){
            intent.putExtra("args"+i+1,args[i]);
        }
        context.startActivity(intent);
    }

}
