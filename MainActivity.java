package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private String urls[],temp[];
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        temp=loadArray("Links",MainActivity.this);
        urls=new String[temp.length];

        for(int i=temp.length-1;i>=0;i--){
            urls[temp.length-1-i]=temp[i];
        }

        if(urls.length!=0){

            setContentView(R.layout.activity_main);

            recyclerView=findViewById(R.id.recyclerView);
            MyAdapter myAdapter=new MyAdapter(this,urls);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            setContentView(R.layout.activity_main2);
        }

    }

    public void saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("URLs", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        editor.commit();
    }
    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("URLs", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
}
