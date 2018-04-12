package com.dongzhex.AdapterPack;

import android.provider.ContactsContract;
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
import java.util.zip.Inflater;

/**
 * Created by ASUS on 2018/4/12.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    public List<Info> mlist;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;  //通知标题
        TextView author;    //通知作者
        TextView time;  //通知发布时间
        CardView cView; //通知卡片
        ImageButton imageButton;//编辑菜单
        public ViewHolder(View v) {
            super(itemView);
            title = (TextView)v.findViewById(R.id.notification_title_text);
            author = (TextView)v.findViewById(R.id.notification_author_text);
            time = (TextView)v.findViewById(R.id.notification_time_text);
            cView = (CardView)v.findViewById(R.id.notification_card);
            imageButton = (ImageButton)v.findViewById(R.id.notification_menu_choose);
        }
    }

    public InfoAdapter(List<Info> mlist) {
        this.mlist = mlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
