package com.dongzhex.photoGetter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.loginwithweb.R;

import static com.example.loginwithweb.R.id.choose;

public class Main3Activity extends PhotoGetter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        image[1] = (ImageView)findViewById(R.id.image23);
        Button take = (Button)findViewById(R.id.take);
        Button choose1 = (Button)findViewById(choose);
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto(1);
            }
        });
    }
}
