package com.dongzhex.AdapterPack;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.someactivities.infosystem.R;

import java.util.List;

/**
 * Created by ASUS on 2018/5/21.
 */

public class NameStringAdapter extends RecyclerView.Adapter<NameStringAdapter.ViewHolder>  {
    public List<String> mlist;
    private Activity act;
    private String data;
    private static final String TAG = "NameStringAdapter";
    public NameStringAdapter(List<String> mlist,Activity act) {
        this.mlist = mlist;
        this.act = act;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageButton button;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_text_unLook);
            button = (ImageButton)view.findViewById(R.id.call_whoNotLook);
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
        data = mlist.get(position);

        Log.d(TAG, data);
        String datas[] = data.split(" ");
        holder.name.setText(datas[0]);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+data.split("|")[1]));
                final AlertDialog.Builder builder = new AlertDialog.Builder(act);
                builder.setCancelable(true);
                builder.setTitle("提示");
                builder.setMessage("您要拨打给他吗？");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(Myapplication.getRealContext(), Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                            Myapplication.getRealContext().startActivity(intent);
                        }
                        else{
                            Toast.makeText(Myapplication.getRealContext(), "您没有相应的权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
