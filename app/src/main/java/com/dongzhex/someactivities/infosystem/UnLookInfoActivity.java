package com.dongzhex.someactivities.infosystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dongzhex.AdapterPack.NameStringAdapter;
import com.dongzhex.entity.slStringInterface;
import com.dongzhex.webservice.RequestUnLookPeople;

import java.util.ArrayList;
import java.util.List;

public class UnLookInfoActivity extends AppCompatActivity {
    private List<String> mlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_look_info);
        String Info_id = getIntent().getStringExtra("Info_id");
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.nameRecycler);
        slStringInterface sl = new slStringInterface() {
            @Override
            public void success(List<String> list) {
                NameStringAdapter nameStringAdapters = new NameStringAdapter(mlist);
                recyclerView.setAdapter(nameStringAdapters);
            }
        };
        RequestUnLookPeople unLookPeople = new RequestUnLookPeople(sl);
        unLookPeople.execute(Info_id);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
