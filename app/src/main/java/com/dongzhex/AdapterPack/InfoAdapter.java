package com.dongzhex.AdapterPack;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dongzhex.NomalService.Myapplication;
import com.dongzhex.OutDialog.InsertInfoDialog;
import com.dongzhex.OutDialog.popMenu;
import com.dongzhex.entity.Info;
import com.dongzhex.entity.slStringInterface;
import com.dongzhex.someactivities.infosystem.InfoContentActivity;
import com.dongzhex.someactivities.infosystem.R;
import com.dongzhex.webservice.ConnectLookProc;
import com.dongzhex.webservice.RemoveInfo;
import com.dongzhex.webservice.updateInfoWebService;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by ASUS on 2018/4/12.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private List<Info> mlist;
    private  int POWER;
    private Activity act;
    private static final String TAG = "InfoAdapter";
   private slStringInterface st;

    Context context;
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

    public InfoAdapter(List<Info> mlist,int power,Activity activity,Context context,slStringInterface sl) {
        this.mlist = mlist;
        this.POWER = power;
        act = activity;
        this.context = context;
         st=sl;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int p =position;
        final Info info = mlist.get(position);
        if(info.getInfo_title().length()>27){
            holder.title.setText(info.getInfo_title().substring(0,27)+"……");
        }
        else{
            holder.title.setText(info.getInfo_title());
        }

        holder.author.setText(info.getInfo_author());
        holder.time.setText(info.getTime());
        //按钮点击事件
        if(POWER==1){
            holder.imageButton.setEnabled(true);
        }else{
            //holder.imageButton.setEnabled(true);
        }

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "ClickImageButton");
               final  popMenu pop = new popMenu(act);
                final View.OnClickListener listener1 = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(v.getId() == R.id.btn_edit_info)
                        {
                            pop.dismiss();
                            Log.d(TAG, info.toString());
                            //内置的弹出框
                            final InsertInfoDialog insertInfoDialog = new InsertInfoDialog(act,R.layout.infodialog,"保存");
                            View.OnClickListener editListener = new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateInfoWebService updateInfoWebService = new updateInfoWebService(new slStringInterface() {
                                        @Override
                                        public void success(String s) {
                                            String t = s.equals("success")?"修改成功":"修改失败";
                                            AlertDialog.Builder builder = new AlertDialog.Builder(act);
                                            builder.setTitle("提示");
                                            builder.setCancelable(true);
                                            builder.setMessage(t);
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    st.success("s");

                                                }
                                            });

                                            builder.show();
                                        }
                                    });
                                    updateInfoWebService.execute(info.getClass_id(),info.getInfo_id(),insertInfoDialog.text_title.getText().toString(),insertInfoDialog.text_content.getText().toString());
                                    insertInfoDialog.cancel();

                                }
                            };
                            insertInfoDialog.setListener(editListener);
                            insertInfoDialog.show();
                            insertInfoDialog.text_title.setText(info.getInfo_title());
                            insertInfoDialog.text_content.setText(info.getInfo_content());
                            //点击事件


                            //视图层改变
                            info.setInfo_content(insertInfoDialog.text_content.getText().toString());
                            info.setInfo_title(insertInfoDialog.text_title.getText().toString());
                            Info infos = new Info(info);
                            mlist.set(position,infos);
                            notifyItemChanged(position);
                        }
                        //进行删除
                        else if(v.getId() == R.id.btn_delete_info){

                            pop.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(act);
                            builder.setTitle("提示");
                            builder.setCancelable(true);
                            builder.setMessage("您确定要删除？");
                            builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String info_id = info.getInfo_id();
                                    RemoveInfo removeInfo = new RemoveInfo();
                                    removeInfo.execute(info_id);
                                    mlist.remove(info);

                                    notifyDataSetChanged();
                                }
                            });
                            builder.setNegativeButton("不", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();

                        }

                    }
                };
                pop.setClick(listener1);
                View rootview = LayoutInflater.from(act).inflate(R.layout.activity_main, null);
                pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);


            }
        });
        //card点击事件
        holder.cView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent = new Intent(Myapplication.getRealContext(), InfoContentActivity.class);
                Log.d(TAG, info.toString());
                intent.putExtra("Class_id",info.getClass_id());
                intent.putExtra("Info_id",info.getInfo_id());
                intent.putExtra("title",info.getInfo_title());
                intent.putExtra("content",info.getInfo_content());
                intent.putExtra("author",info.getInfo_author());
                intent.putExtra("time",info.getTime());
                intent.putExtra("looked_num",info.getLooked_num());
                ConnectLookProc lookProc = new ConnectLookProc();
                SharedPreferences sharedPreferences1 = Myapplication.getRealContext().getSharedPreferences("presentUser",MODE_PRIVATE);
                String username = sharedPreferences1.getString("Username","");
                lookProc.execute(username,info.getInfo_id());
                Myapplication.getRealContext().startActivity(intent);

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
