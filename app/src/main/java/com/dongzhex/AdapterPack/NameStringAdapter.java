package com.dongzhex.AdapterPack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongzhex.someactivities.infosystem.R;

import java.util.List;

/**
 * Created by ASUS on 2018/5/21.
 */

public class NameStringAdapter extends RecyclerView.Adapter<NameStringAdapter.ViewHolder>  {
    public List<String> mlist;

    public NameStringAdapter(List<String> mlist) {
        this.mlist = mlist;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_text_unLook);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nameitem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = mlist.get(position);
        holder.name.setText(data);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
