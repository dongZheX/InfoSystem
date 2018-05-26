package com.dongzhex.someactivities.infosystem;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dongzhex.NomalService.Myapplication;

public class HelpActivity extends AppCompatActivity {
    private TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        //功能未写
        textView = (TextView)findViewById(R.id.phone_manager_Text_onHelp);
        //打电话
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"15650111502"));
                final AlertDialog.Builder builder = new AlertDialog.Builder(HelpActivity.this);
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
}
