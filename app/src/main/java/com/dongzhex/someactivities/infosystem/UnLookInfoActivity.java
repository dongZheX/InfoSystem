package com.dongzhex.someactivities.infosystem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.dongzhex.AdapterPack.NameStringAdapter;
import com.dongzhex.entity.slStringInterface;
import com.dongzhex.webservice.RequestUnLookPeople;

import java.util.ArrayList;
import java.util.List;

public class UnLookInfoActivity extends AppCompatActivity {
    private List<String> mlist = new ArrayList<>();
    private static final String TAG = "UnLookInfoActivity";
    private  RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_look_info);
        String Info_id = getIntent().getStringExtra("Info_id");
        recyclerView = (RecyclerView)findViewById(R.id.nameRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toolbar toolbar = (Toolbar)findViewById(R.id.setting_toolbar_unlook) ;

        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        slStringInterface sl = new slStringInterface() {
            @Override
            public void success(String s) {
                Log.d(TAG, s);
                    String[] d = s.split("/");
                    for(int i = 0;i<d.length;i++){
                        mlist.add(d[i]);
                    }
                if(mlist!=null) {

                    NameStringAdapter nameStringAdapters = new NameStringAdapter(mlist,UnLookInfoActivity.this);
                    recyclerView.setAdapter(nameStringAdapters);
                }
            }
        };
        RequestUnLookPeople unLookPeople = new RequestUnLookPeople(sl);
        Log.d(TAG,Info_id);
        unLookPeople.execute(Info_id);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
          finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
