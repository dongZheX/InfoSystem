package com.dongzhex.someactivities.infosystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongzhex.NomalService.BaseTool;
import com.dongzhex.NomalService.MessageBox;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.UserX;
import com.dongzhex.webservice.PerfectInfoUserX;
import com.dongzhex.webservice.UploadBitmap;
import com.lljjcoder.citypickerview.widget.CityPicker;

import de.hdodenhof.circleimageview.CircleImageView;


public class Personal_Center extends PhotoGetter {
    private Spinner sex;
    private TextView username;
    private TextView Class_id;
    private EditText name;
    private TextView address;
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

        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        user = (UserX)getIntent().getSerializableExtra("userx");
        onInitView();
        if(user!=null)
        Log.d(TAG, user.toString());
        setButton();
    }
    //设置按钮
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
                    if(!BaseTool.isMobileNumber(sphone)){
                        MessageBox.showMessageBox(Personal_Center.this,"提示","电话号码不合法",true).show();

                    }
                    else if(!BaseTool.ChineseNameTest(sname)){
                        MessageBox.showMessageBox(Personal_Center.this,"提示","姓名不合法",true).show();
                    } else if(!BaseTool.isQQ(sQQ)){
                        MessageBox.showMessageBox(Personal_Center.this,"提示","QQ不合法",true).show();
                    }else if(!BaseTool.isDay(year.getText().toString(),mon.getText().toString(),day.getText().toString())){
                        MessageBox.showMessageBox(Personal_Center.this,"提示","出生日期不合法",true).show();
                    }
                    else {
                        PerfectInfoUserX service = new PerfectInfoUserX();
                        service.execute(temp);
                        if (bitmap[4] != null) {
                            UploadBitmap uploadBitmap = new UploadBitmap(username.getText().toString());
                            uploadBitmap.execute(bitmap[4]);
                        }
                    }
                }catch (Exception e){
                    MessageBox.showMessageBox(Personal_Center.this,"bug提示","未调试的错误002",false);
                }
                MessageBox.showMessageBox(Personal_Center.this,"提示","保存成功",false);
                sex.setEnabled(false);
                name.setEnabled(false);
                address.setEnabled(false);
                QQ.setEnabled(false);
                phone.setEnabled(false);
                year.setEnabled(false);
                mon.setEnabled(false);
                day.setEnabled(false);
                personal_choose_photo.setEnabled(false);
                edit.setText("编辑");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许编辑
                if(edit.getText().toString().equals("编辑")) {
                    sex.setEnabled(true);
                    name.setEnabled(true);
                    address.setEnabled(true);
                    QQ.setEnabled(true);
                    phone.setEnabled(true);
                    year.setEnabled(true);
                    mon.setEnabled(true);
                    day.setEnabled(true);

                    personal_choose_photo.setEnabled(true);

                    edit.setText("取消编辑");
                    save.setEnabled(true);
                }
                else{
                    sex.setEnabled(false);
                    name.setEnabled(false);
                    address.setEnabled(false);
                    QQ.setEnabled(false);
                    phone.setEnabled(false);
                    year.setEnabled(false);
                    mon.setEnabled(false);
                    day.setEnabled(false);
                    personal_choose_photo.setEnabled(false);

                    edit.setText("编辑");
                    save.setEnabled(false);
                }
            }
        });
        personal_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    circleImageView[4] = image;
                    choosePhoto(4,userName);

                }catch(Exception ex){
                    MessageBox.showMessageBox(Personal_Center.this,"bug提示","未调试的错误",false);
                }
            }
        });
        //地址选择器
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseArea(getWindow().getDecorView());
            }
        });
    }
    private void onInitView(){
        sex = (Spinner)findViewById(R.id.sp_personal_sex);
        username = (TextView)findViewById(R.id.edit_personal_username);
        Class_id = (TextView)findViewById(R.id.edit_personal_Class_id);
        name = (EditText) findViewById(R.id.edit_personal_name);
        address = (TextView)findViewById(R.id.sp_personal_address);
        year = (EditText)findViewById(R.id.sp_personal_year);
        mon = (EditText)findViewById(R.id.sp_personal_month);
        day = (EditText)findViewById(R.id.sp_personal_day);
        phone = (EditText)findViewById(R.id.sp_personal_phone);
        QQ = (EditText)findViewById(R.id.edit_personal_QQ);
        image = (CircleImageView)findViewById(R.id.personal_image_circle);
        String ssex,susername,sClass_id,sname,saddress,sbirth,sQQ,sphone;
        String birtht[] = new String[3];

        //string由intent赋值
        //这里进行了修改
        if(user!=null) {
            ssex = user.getUser_sex();
            susername = user.getUsername();
            sClass_id = user.getClass_id();
            sname = user.getUser_name();
            saddress = user.getUser_address();
            sbirth = user.getBirth();
            sQQ = user.getUser_QQ();
            sphone = user.getUser_phone();
            try {
                if (ssex.equals("男"))
                    sex.setSelection(0);
                else
                    sex.setSelection(1);//可能出错
            }catch(Exception e){
                Log.d(TAG, "spinner出错");
            }
            username.setText(susername);
            Class_id.setText(sClass_id);
            name.setText(sname);
            address.setText(saddress);
            //格式处理
            if(birtht!=null&&!birtht.equals("")) {
                birtht = sbirth.split("/");
                if(birtht.length==3) {
                    year.setText(birtht[0]);
                    mon.setText(birtht[1]);
                    day.setText(birtht[2]);
                }
            }
            QQ.setText(sQQ);
            phone.setText(sphone);
            address.setText(saddress);
            if(user.getUser_image()!=null&&user.getUser_image()!="")
                Glide.with(this)
                        . load(NetUnit.URL+"/InfoSystem"+user.getUser_image()).error(R.drawable.morentouxiang).into(image);
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
    public void chooseArea(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域
        }
    }
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(Personal_Center.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(8)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                address.setText(province.trim() +city.trim() + district.trim());
            }
        });
    }

}
