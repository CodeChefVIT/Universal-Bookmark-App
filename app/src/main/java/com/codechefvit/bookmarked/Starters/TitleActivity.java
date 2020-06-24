package com.codechefvit.bookmarked.Starters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codechefvit.bookmarked.Authentication.Login;
import com.codechefvit.bookmarked.Backend.TinyDB;
import com.codechefvit.bookmarked.Main.Main;
import com.codechefvit.bookmarked.R;

public class TitleActivity extends AppCompatActivity {

    boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        TinyDB tinyDB=new TinyDB(this);
        state=tinyDB.getBoolean("State");
        final boolean flag=tinyDB.getBoolean("Flag");

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(state){
                    if(!flag){
                        Intent intent=new Intent(getBaseContext(), Main.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(getBaseContext(), Login.class);
                        startActivity(intent);
                    }
                }
                else{
                    Intent intent=new Intent(getBaseContext(),TutorialActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);

    }
}
