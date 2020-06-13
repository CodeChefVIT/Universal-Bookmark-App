package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String urls[];
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urls=loadArray("Links",MainActivity.this);

        if(urls.length!=0){

            setContentView(R.layout.activity_main);

            recyclerView=findViewById(R.id.recyclerView);
            final MyAdapter myAdapter=new MyAdapter(this,urls);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            myAdapter.onItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Uri uri=Uri.parse(urls[position]);
                    Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            myAdapter.onItemClickListener2(new MyAdapter.OnItemClickListener2() {
                @Override
                public void onItemClick2(int position) {

                    urls=loadArray("Links",MainActivity.this);
                    if(urls.length>1)
                    {
                    for(int i=position;i<urls.length-1;i++){
                        urls[i]=urls[i+1];
                    }
                    String temp[]=new String[urls.length-1];
                    for(int i=0;i<urls.length-1;i++){
                        temp[i]=urls[i];
                    }
                    saveArray(temp,"Links",MainActivity.this);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    }
                    else{
                        String a[]=new String[0];
                        saveArray(a,"Links",MainActivity.this);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                }
            });

        }
        else{
            setContentView(R.layout.activity_main2);
        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                pullToRefresh.setRefreshing(true);
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
    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("URLs", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
}
