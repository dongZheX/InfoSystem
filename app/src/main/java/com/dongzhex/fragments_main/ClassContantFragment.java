package com.dongzhex.fragments_main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dongzhex.AdapterPack.UserXAdapter;
import com.dongzhex.entity.UserX;
import com.dongzhex.someactivities.infosystem.R;

import java.util.List;

/**
 * Created by ASUS on 2018/4/13.
 */

public class ClassContantFragment extends Fragment {
    private View view;
    private List<UserX> mlist;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class_contact_layout,container);
        recyclerView = (RecyclerView)view.findViewById(R.id.contact_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        initmlist();
        UserXAdapter userXAdapter = new UserXAdapter(mlist);

        //权限申请
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    //初始化
    private void initmlist(){
        mlist = null;
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
