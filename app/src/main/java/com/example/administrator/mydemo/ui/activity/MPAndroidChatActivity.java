package com.example.administrator.mydemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.mydemo.R;

public class MPAndroidChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chat);
    }

    public void line(View view){
        Intent intent = new Intent(this, LineActivity.class);
        startActivity(intent);
    }

    public void bar(View view){
        Intent intent = new Intent(this, BarActivity.class);
        startActivity(intent);
    }

    public void pie(View view){
        Intent intent = new Intent(this, PieActivity.class);
        startActivity(intent);
    }

}
