package com.yfz.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击开启前端服务
    public void openService(View view){
        Intent service = new Intent(getApplicationContext(), ForegroundServiceTest.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startService(service);
        }
    }
}