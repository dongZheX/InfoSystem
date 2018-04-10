package com.dongzhex.someactivities.infosystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class perfect_information_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_information_user);
        Toolbar toolbar_perfect = (Toolbar)findViewById(R.id.perfect_toolbar);
        setSupportActionBar(toolbar_perfect);
        //启动逻辑
        //

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perfect_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.perfect_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(perfect_information_user.this);
                builder.setTitle("帮助");
                builder.setCancelable(true);
                builder.setMessage("应管理员要求：\n您必须完善下列信息，如有问题请联系：15650111502");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    //启动
    public static void activityStart(Context context,String...args){
        Intent intent = new Intent(context,perfect_information_user.class);
        for(int i = 0;i<args.length;i++){
            intent.putExtra("args"+i+1,args[i]);
        }
        context.startActivity(intent);

    }
}
