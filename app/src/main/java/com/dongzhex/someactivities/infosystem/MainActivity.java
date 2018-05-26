package com.dongzhex.someactivities.infosystem;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.dongzhex.AdapterPack.MyfragmentPageAdapter;
import com.dongzhex.NomalService.NetUnit;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.User;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.successListener;
import com.dongzhex.fragments_main.ClassContactFragment;
import com.dongzhex.fragments_main.NotificationFragment;
import com.dongzhex.fragments_main.ViewResourceFragment;
import com.dongzhex.jsonService.JsonService;
import com.dongzhex.webservice.LoginService;
import com.dongzhex.webservice.getUserXFromWeb;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPage;
    private BottomNavigationBar navigation;
    public final static int PAGE_ONE = 0;
    public final static  int PAGE_TWO = 1;
    public final static int PAGE_THREE = 2;
    public  String usernameP;
    private List<Fragment> fragmentList;
    private MyfragmentPageAdapter myfragmentPageAdapter;
    private UserX userx;
    private String username;
    private NavigationView navigationView;
    private DrawerLayout mdrawerLayout;
    private CircleImageView circleImageView;
    private TextView textUsername;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameP = getIntent().getStringExtra("username");
        navigation = (BottomNavigationBar)findViewById(R.id.navigation);
        navigation.setFirstSelectedPosition(0);
        viewPage = (ViewPager)findViewById(R.id.fragment_pager);
        viewPage.setCurrentItem(0);
        //设置碎片
        initFragmentQueue();
        initNavigation();
        initNavigationClick();
        initViewPageScroll();
        myfragmentPageAdapter = new MyfragmentPageAdapter(getSupportFragmentManager(),fragmentList);
        viewPage.setAdapter(myfragmentPageAdapter);
        //初始化配置

        SharedPreferences sharedPreferences1 = getSharedPreferences("presentUser",MODE_PRIVATE);
        username = sharedPreferences1.getString("Username","");
        getUserXFromWeb getUserX = new getUserXFromWeb(new successListener() {
            @Override
            public void success(User x) {
            }
            @Override
            public void success(String x) {
                userx = JsonService.jsonToJavaBean(x,UserX.class);
                String image = null;
                Log.d(TAG, userx.toString());
                if(userx!=null)
                image = NetUnit.URL + "/InfoSystem" + userx.getUser_image();

                if(userx!=null) {
                    Glide.with(MainActivity.this).load(image)
                            .error(R.drawable.morentouxiang)
                            .centerCrop()
                            .into(circleImageView);
                }
                else{
                    Glide.with(MainActivity.this).load(R.drawable.morentouxiang)
                            .into(circleImageView);
                }
                textUsername.setText(userx.getUser_name());
            }

            @Override
            public void successInfo(List<Info> mlist) {

            }

            @Override
            public void successUserX(List<UserX> mlist) {

            }
        });

        //上面使用最新技术，存在延迟问题
        //配置左上按钮
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        }
        //初始化拖拽栏

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        mdrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        View headView = navigationView.getHeaderView(0);//划重点
        circleImageView = (CircleImageView)headView.findViewById(R.id.icon_image);

        textUsername = (TextView)headView.findViewById(R.id.username);
        
        getUserX.execute(username);
        //权限申请
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},5);
        }
        setClickNavi();
    }
    //初始化碎片队列
    void initFragmentQueue(){
        fragmentList = new ArrayList<>();
        NotificationFragment notificationFragment = new NotificationFragment();
        ClassContactFragment classContactFragment = new ClassContactFragment();
        ViewResourceFragment viewResourceFragment = new ViewResourceFragment();
        fragmentList.add(notificationFragment);
        fragmentList.add(classContactFragment);
        fragmentList.add(viewResourceFragment);
    }
    //设置拖拽点击事件
    void setClickNavi(){
        navigationView.setCheckedItem(R.id.personal_item);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.personal_item:
                    {
                        Intent intent = new Intent(MainActivity.this,Personal_Center.class);
                        if(userx!=null) {
                            intent.putExtra("userx", userx);
                        }

                        mdrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    }
                    case R.id.myclass_item:{
                        Intent intent = new Intent(MainActivity.this,MyClassActivity.class);
                        mdrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    }
                    case R.id.change_pass_item:{
                        Intent intent = new Intent(MainActivity.this,ResetPassword.class);
                        intent.putExtra("userx",userx);
                        mdrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    }
                    case R.id.setting_item:{
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        mdrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    }
                    case R.id.request_help_item:{
                        Intent intent = new Intent(MainActivity.this,HelpActivity.class);
                        mdrawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    }
                    case R.id.exit:{
                        final Intent intent = new Intent(MainActivity.this,LoginService.class);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setMessage("您确定要退出？");
                        builder.setTitle("提示");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences mainSetting = getSharedPreferences("mainSetting",MODE_PRIVATE);
                                SharedPreferences.Editor ed = mainSetting.edit();
                                ed.putBoolean("isLogin",false);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                        mdrawerLayout.closeDrawers();

                        break;
                    }
                }
                return false;
            }
        });
    }
    //初始化滑动碎片时间
    private void initViewPageScroll(){
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //初始化导航栏
    private void  initNavigation(){
        navigation.setMode(BottomNavigationBar.MODE_FIXED);
        navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigation.addItem(new BottomNavigationItem(R.drawable.notification_item,"班级通知"));
        navigation.addItem(new BottomNavigationItem(R.drawable.navibuttom_contact,"联系班级"));
        navigation.addItem(new BottomNavigationItem(R.drawable.resource_item,"资源通道"));
        navigation.setFirstSelectedPosition(0);
        navigation.initialise();

    }
    private void initNavigationClick(){
        navigation.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPage.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
                switch (position){

                }
            }

            @Override
            public void onTabReselected(int position) {
                switch (position){

                }
            }
        });
    }

    //加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(navigation.getCurrentSelectedPosition()==0) {
            //加上权限处理
            SharedPreferences sharedPreferences = getSharedPreferences(usernameP,MODE_PRIVATE);
            if(sharedPreferences.getInt("Power",0)==1) {
                getMenuInflater().inflate(R.menu.notification_menu, menu);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }
    //菜单点击--未完成

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 5:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(),"您可以打电话",Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(getContext(),"您可能无法再本应用中启动电话",Toast.LENGTH_SHORT);
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
