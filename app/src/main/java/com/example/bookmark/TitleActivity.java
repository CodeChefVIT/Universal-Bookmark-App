package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class TitleActivity extends AppCompatActivity {

    String a[]=new String[1];

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

        a[0]="0";
        saveArray(a, "Flag", getApplicationContext());
    }
    public int loadState(String state, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("First", 0);
        int value = prefs.getInt(state, 0);
        return value;
    }
    public void saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("URLs", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        editor.commit();
    }
}
