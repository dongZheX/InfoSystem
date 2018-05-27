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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongzhex.AdapterPack.UserXAdapter;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.User;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.successListener;
import com.dongzhex.jsonService.JsonService;
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
    private static final String TAG = "ClassContactFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView title_main = (TextView) getActivity().findViewById(R.id.main_Title);
        title_main.setText("主界面");
        view = inflater.inflate(R.layout.class_contact_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.contact_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_contact);
        setSwipeRefreshLayout();//设置刷新事件
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        Class_id = sharedPreferences1.getString("Class_id","");//读取配置
        Log.d(TAG, Class_id);
        initmlist();
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
                Log.d(TAG, x);
                mlist = JsonService.jsonToList(x,UserX.class);
                if(mlist!=null) {
                    UserXAdapter userXAdapter = new UserXAdapter(mlist, view.getContext(),getActivity());
                    recyclerView.setAdapter(userXAdapter);
                }
            }

            @Override
            public void successInfo(List<Info> mlist) {


            }

            @Override
            public void successUserX(List<UserX> mlist) {

            }
        };
        RequestContantList requestContantList = new RequestContantList(sl);
        requestContantList.execute(Class_id);
    }
    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }
    private void Refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RequestContantList requestContantList = new RequestContantList(sl);
                        requestContantList.execute(Class_id);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    //权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
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
