package com.dongzhex.fragments_main;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dongzhex.AdapterPack.UserXAdapter;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.User;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.successListener;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.webservice.RequestContantList;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2018/4/13.
 */

public class ClassContactFragment extends Fragment {
    private View view;
    private List<UserX> mlist;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String Class_id;
    private successListener sl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_contact_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.contact_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_contact);
        setSwipeRefreshLayout();//设置刷新事件
        initmlist();
        UserXAdapter userXAdapter = new UserXAdapter(mlist);
        recyclerView.setAdapter(userXAdapter);//设置列表
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        Class_id = sharedPreferences1.getString("Class_id","");//读取配置
        //权限申请
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
        }

        return view;
    }
    //初始化
    private void initmlist(){
        sl = new successListener() {
            @Override
            public void success(User x) {

            }

            @Override
            public void success(String x) {

            }

            @Override
            public void successInfo(List<Info> mlist) {

            }

            @Override
            public void successUserX(List<UserX> mlist) {
                UserXAdapter userXAdapter = new UserXAdapter(mlist);
                recyclerView.setAdapter(userXAdapter);
            }
        };
        RequestContantList requestContantList = new RequestContantList(sl);
        requestContantList.execute(Class_id);
    }
    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefresh();
            }
        });
    }
    private void onRefresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initmlist();
                        recyclerView.getAdapter().notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    //权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }
                else{
                    Toast.makeText(getContext(),"您可能无法再本应用中启动电话",Toast.LENGTH_SHORT);
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
