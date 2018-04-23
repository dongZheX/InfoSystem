package com.dongzhex.fragments_main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongzhex.AdapterPack.ResourceAdapter;
import com.dongzhex.entity.ResourceData;
import com.dongzhex.someactivities.infosystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/4/13.
 */

public class ViewResourceFragment extends Fragment {
    private List<ResourceData> mlist;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resource_entry_layout,container,false);
        initList();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.resource_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ResourceAdapter resourceAdapter = new ResourceAdapter(mlist);
        recyclerView.setAdapter(resourceAdapter);
        return view;
    }
    public void initList(){
        mlist = new ArrayList<ResourceData>();
        mlist.add(new ResourceData("校园信息化门户","https://portal.wh.sdu.edu.cn",R.drawable.shandongdaxue));
    }
}
