package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TutorialActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        saveState(1,"First", this);
        String a[]={};
        saveArray(a,"Links",this);

        button=findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("URLs", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        editor.commit();
    }

    public void saveState(int value, String state, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("First", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(state, value);
        editor.commit();
    }
}
