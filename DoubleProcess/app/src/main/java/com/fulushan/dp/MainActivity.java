package com.fulushan.dp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fulushan.dp.service.LocalService;
import com.fulushan.dp.service.RemoteService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startService(new Intent(this, LocalService.class));
        this.startService(new Intent(this, RemoteService.class));
    }
}
