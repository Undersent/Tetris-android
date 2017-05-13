package com.example.rafal.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void start(View v){
        Intent myIntent = new Intent(Start.this, MainActivity.class);System.out.println("aaaa");
        startActivity(myIntent);
    }
}
