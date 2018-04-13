package com.dongzhex.AdapterPack;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongzhex.entity.Info;
import com.dongzhex.someactivities.infosystem.R;

import java.util.List;


/**
 * Created by ASUS on 2018/4/12.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private List<Info> mlist;
    private  int POWER;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;  //通知标题
        TextView author;    //通知作者
        TextView time;  //通知发布时间
        CardView cView; //通知卡片
        ImageButton imageButton;//编辑菜单
        public ViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.notification_title_text);
            author = (TextView)v.findViewById(R.id.notification_author_text);
            time = (TextView)v.findViewById(R.id.notification_time_text);
            cView = (CardView)v;
            imageButton = (ImageButton)v.findViewById(R.id.notification_menu_choose);
        }
    }

    public InfoAdapter(List<Info> mlist,int power) {
        this.mlist = mlist;
        this.POWER = power;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info info = mlist.get(position);
        holder.title.setText(info.getInfo_title());
        holder.author.setText(info.getInfo_author());
        holder.time.setText(info.getTime());
        //按钮点击事件
        if(POWER==1){
            holder.imageButton.setEnabled(false);
        }
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //card点击事件
        holder.cView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public void removeData(int position){
        mlist.remove(position);
        //简单测试
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }



}
