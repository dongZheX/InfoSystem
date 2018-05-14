package com.dongzhex.fragments_main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongzhex.AdapterPack.InfoAdapter;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.Info;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.webservice.RequestNotificationList;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ASUS on 2018/4/12.
 */

public class NotificationFragment extends Fragment {
    private List<Info> mlist=null;
    private  RecyclerView recyclerView;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String username;
    private String Class_id;

    public NotificationFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container,false);
        TextView title_main = (TextView) getActivity().findViewById(R.id.main_Title);
        title_main.setText("班级通知");
        //初始化
        initlist();
        recyclerView = (RecyclerView) view.findViewById(R.id.notification_recycler);
        InfoAdapter infoAdapter = new InfoAdapter(mlist,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(infoAdapter);
        setHasOptionsMenu(true);
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        Class_id = sharedPreferences1.getString("Class_id","");
        username = sharedPreferences1.getString("Username","");
        return view;
    }
    private void initlist(){
        RequestNotificationList requestNotificationList = new RequestNotificationList(mlist);
        requestNotificationList.execute(Class_id);
    }
    private void setSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestNotificationList requestNotificationList = new RequestNotificationList(mlist);
                requestNotificationList.execute(Class_id);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);

        if(sharedPreferences1.getInt("Power",0)==1) {
            inflater.inflate(R.menu.notification_menu,menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_notification:{

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
