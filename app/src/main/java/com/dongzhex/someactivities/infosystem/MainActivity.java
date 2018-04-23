package com.dongzhex.someactivities.infosystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dongzhex.AdapterPack.MyfragmentPageAdapter;
import com.dongzhex.fragments_main.ClassContactFragment;
import com.dongzhex.fragments_main.NotificationFragment;
import com.dongzhex.fragments_main.ViewResourceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPage;
    private BottomNavigationBar navigation;
    public final static int PAGE_ONE = 0;
    public final static  int PAGE_TWO = 1;
    public final static int PAGE_THREE = 2;
    private List<Fragment> fragmentList;
    private MyfragmentPageAdapter myfragmentPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }
    //初始化碎片队列
    void initFragmentQueue(){
        fragmentList = new ArrayList<Fragment>();
        NotificationFragment notificationFragment = new NotificationFragment();
        ClassContactFragment classContactFragment = new ClassContactFragment();
        ViewResourceFragment viewResourceFragment = new ViewResourceFragment();
        fragmentList.add(notificationFragment);
        fragmentList.add(classContactFragment);
        fragmentList.add(viewResourceFragment);
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
            getMenuInflater().inflate(R.menu.notification_menu, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }
    //菜单点击--未完成

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }

}
