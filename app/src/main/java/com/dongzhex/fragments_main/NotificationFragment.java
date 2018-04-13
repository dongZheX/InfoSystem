package com.dongzhex.fragments_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongzhex.AdapterPack.InfoAdapter;
import com.dongzhex.entity.Info;
import com.dongzhex.someactivities.infosystem.R;

import java.util.List;

/**
 * Created by ASUS on 2018/4/12.
 */

public class NotificationFragment extends Fragment {
    private List<Info> mlist=null;
    private  RecyclerView recyclerView;
    private View view;
    public NotificationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notification_fragment,container);
        TextView title_main = (TextView)view.findViewById(R.id.main_Title);
        title_main.setText("班级通知");
        //初始化
        initlist();
        recyclerView = (RecyclerView) view.findViewById(R.id.notification_recycler);
        InfoAdapter infoAdapter = new InfoAdapter(mlist,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(infoAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private void initlist(){
        mlist = null;
    }



}
