package com.example.bookmark.Starters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.Main.Main;
import com.example.bookmark.Main.SampleActivity;
import com.example.bookmark.R;

public class TitleActivity extends AppCompatActivity {

    boolean state=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        TinyDB tinyDB=new TinyDB(this);
        state=tinyDB.getBoolean("State");

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(state){
                    Intent intent=new Intent(getBaseContext(), Main.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(getBaseContext(),TutorialActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);

    }
}
