package com.example.administrator.mydemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.mydemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void googlemap(View v) {
        Intent intent = new Intent(this, GoogleMapActivity.class);
        startActivity(intent);
    }

    public void hunxin(View v) {
        Intent intent = new Intent(this, IMActivity.class);
        startActivity(intent);
    }

    public void mpandroidchat(View v) {
        Intent intent = new Intent(this, MPAndroidChatActivity.class);
        startActivity(intent);
    }
}
