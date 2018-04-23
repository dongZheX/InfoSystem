package com.dongzhex.AdapterPack;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.UserX;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.someactivities.infosystem.UserXInfoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 2018/4/13.
 */

public class UserXAdapter extends RecyclerView.Adapter<UserXAdapter.ViewHolder> {
    private List<UserX> mlist;
    private Context mContext;
    public UserXAdapter(List<UserX> mlist) {
        this.mlist = mlist;
    }



    public static class ViewHolder extends  RecyclerView.ViewHolder{
        CircleImageView image;
        TextView User_info;
        FloatingActionButton call_button;
        CardView cView;
        public ViewHolder(View view) {
            super(view);
            image = (CircleImageView)view.findViewById(R.id.contact_image_user);
            User_info = (TextView)view.findViewById(R.id.user_info_text);
            call_button = (FloatingActionButton)view.findViewById(R.id.call_button);
            cView = (CardView)view;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_contact_item,parent,false);
        ViewHolder viewHolder =  new ViewHolder(view);
        mContext = view.getContext();
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserX userX = mlist.get(position);
        String user_name = userX.getUser_name();
        final String user_phone = userX.getUser_phone();
        holder.User_info.setText(user_name+":"+user_phone);
        //拨号点击事件
        holder.call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+user_phone));
                if(ContextCompat.checkSelfPermission(Myapplication.getRealContext(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    Myapplication.getRealContext().startActivity(intent);
                }
                else{
                    Toast.makeText(Myapplication.getRealContext(), "您没有相应的权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //view点击事件
        holder.cView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myapplication.getRealContext(), UserXInfoActivity.class);
                intent.putExtra("Username",userX.getUsername());
                intent.putExtra("User_name",userX.getUser_name());
                intent.putExtra("Class_id",userX.getClass_id());
                intent.putExtra("User_sex",userX.getUser_sex());
                intent.putExtra("USer_phone",userX.getUser_phone());
                intent.putExtra("User_address",userX.getUser_address());
                intent.putExtra("User_QQ",userX.getUser_QQ());
                intent.putExtra("User_image",userX.getUser_image());
                intent.putExtra("User_birth",userX.getBirth());
                Myapplication.getRealContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
