package com.dongzhex.someactivities.infosystem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dongzhex.AdapterPack.TeacherAdapter;
import com.dongzhex.entity.Teacher;
import com.dongzhex.entity.slStringInterface;
import com.dongzhex.jsonService.JsonService;
import com.dongzhex.webservice.requestTeacherList;

import java.util.List;


public class tearcher_Show_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Teacher> mlist;
    private TeacherAdapter teacherAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tearcher__show_);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_teacher);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_teacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String Class_id = getSharedPreferences("presentUser",MODE_PRIVATE).getString("Class_id","");
        ActionBar  actionBar = getSupportActionBar();

        if(actionBar!=null)
        actionBar.setDisplayHomeAsUpEnabled(true);
        requestTeacherList request = new requestTeacherList(new slStringInterface() {
            @Override
            public void success(String s) {
                if(s!=null) {
                    mlist = JsonService.jsonToList(s, Teacher.class);
                    teacherAdapter = new TeacherAdapter(mlist, tearcher_Show_Activity.this);

                    recyclerView.setAdapter(teacherAdapter);
                }
            }
        });
        request.execute(Class_id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
