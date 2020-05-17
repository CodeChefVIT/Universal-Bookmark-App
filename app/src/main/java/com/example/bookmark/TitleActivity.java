package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class TitleActivity extends AppCompatActivity {

    ImageView title_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                int value=loadState("First",TitleActivity.this);
                if(value==1){
                    Intent intent=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(getBaseContext(),TutorialActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);
    }
    public int loadState(String state, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("First", 0);
        int value = prefs.getInt(state, 0);
        return value;
    }
}
