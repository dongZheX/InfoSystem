package com.dongzhex.AdapterPack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.entity.ResourceData;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.someactivities.infosystem.ViewResource;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 2018/4/13.
 */

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {
    public List<ResourceData> mlist;
    private Context mContext;
    public ResourceAdapter(List<ResourceData> mlist) {
        this.mlist = mlist;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView resource_name;
        CircleImageView circleImageView;
        Button button_enter;
        public ViewHolder(View view) {
            super(view);
             resource_name = (TextView) view.findViewById(R.id.resource_name);
            circleImageView = (CircleImageView)view.findViewById(R.id.resource_image);
            button_enter = (Button)view.findViewById(R.id.button_enter_resource);
        }

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item,parent,false);
        mContext = view.getContext();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ResourceData resourceData = mlist.get(position);
        holder.resource_name.setText(resourceData.getName());
        if(resourceData.getImageId()!=0) {
            Glide.with(mContext).load(resourceData.getImageId())
                    .centerCrop()
                    .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                    .error(R.drawable.shandongdaxue)
                    .into(holder.circleImageView);
        }
        //进入点击事件
        holder.button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Myapplication.getRealContext(), ViewResource.class);
                intent.putExtra("url",resourceData.getUrl());
                intent.putExtra("title",resourceData.getName());
                Myapplication.getRealContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
