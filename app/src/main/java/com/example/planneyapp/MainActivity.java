package com.example.planneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private int loading_time=3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home=new Intent(MainActivity.this, ActivityLogin.class);
                startActivity(home);
                finish();
            }
        },loading_time);

    }
}