package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class datash extends Activity {

RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datashow);

        relativeLayout = findViewById(R.id.datt);

        //ChatAdapter b = new ChatAdapter();

        int i = 0;

        if (i%3==0){
            relativeLayout.setGravity(Gravity.LEFT);
        }

        else{
            relativeLayout.setGravity(Gravity.RIGHT);

        }

    }}
