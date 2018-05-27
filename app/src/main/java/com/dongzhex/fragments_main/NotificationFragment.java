package com.dongzhex.fragments_main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongzhex.AdapterPack.InfoAdapter;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.OutDialog.InsertInfoDialog;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.User;
import com.dongzhex.entity.UserX;
import com.dongzhex.entity.slStringInterface;
import com.dongzhex.entity.successListener;
import com.dongzhex.jsonService.JsonService;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.webservice.RequestNotificationList;
import com.dongzhex.webservice.publishInfoWebService;

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
    private successListener sl;
    private View v;
    private ImageButton flbutton;
    private static final String TAG = "NotificationFragment";

    public NotificationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container,false);
        TextView title_main = (TextView) getActivity().findViewById(R.id.main_Title);
        title_main.setText("主界面");
        //初始化
        
        recyclerView = (RecyclerView) view.findViewById(R.id.notification_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
        Class_id = sharedPreferences1.getString("Class_id","");
        Log.d(TAG, Class_id);
        username = sharedPreferences1.getString("Username","");
        initlist();

        flbutton = (ImageButton) view.findViewById(R.id.add_info_floatButton);
        if(sharedPreferences1.getInt("Power",0)==1)
            flbutton.setEnabled(true);
        else{
            flbutton.setEnabled(false);
        }
        flbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View.OnClickListener listener1;
                final InsertInfoDialog dialog  = new InsertInfoDialog(getActivity(),R.layout.infodialog,"发布");
                listener1  = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = dialog.text_title.getText().toString();
                        String content = dialog.text_content.getText().toString();
                        publishInfoWebService publishInfoWebService = new publishInfoWebService();
                        publishInfoWebService.execute(Class_id,username,title,content);
                        RequestNotificationList requestNotificationList = new RequestNotificationList(sl);
                        requestNotificationList.execute(Class_id);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        dialog.cancel();
                    }
                };
                setHasOptionsMenu(true);
                dialog.setListener(listener1);

                        dialog.show();

            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_notification) ;//设置刷新事件
      setSwipeRefresh();
        return view;
    }
    private void initlist(){
        sl = new successListener() {
            @Override
            public void success(User x) {

            }

            @Override
            public void success(String x) {
                Log.d(TAG, x);
                mlist = JsonService.jsonToList(x,Info.class);
                if(mlist!=null) {
                    InfoAdapter infoAdapter = new InfoAdapter(mlist, 1, getActivity(), view.getContext(), new slStringInterface() {
                        @Override
                        public void success(String s) {
                            Refresh();
                        }
                    });
                    recyclerView.setAdapter(infoAdapter);
                }
            }

            @Override
            public void successInfo(List<Info> mlist) {

            }

            @Override
            public void successUserX(List<UserX> mlist) {

            }
        };
        RequestNotificationList requestNotificationList = new RequestNotificationList(sl);
        requestNotificationList.execute(Class_id);
    }
    private void setSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }
    private void Refresh(){
        new  Thread(new Runnable() {
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
                        RequestNotificationList requestNotificationList = new RequestNotificationList(sl);
                        requestNotificationList.execute(Class_id);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }



}
