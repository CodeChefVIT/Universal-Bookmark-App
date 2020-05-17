package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;

public class SendActivity extends AppCompatActivity {

    private WebView webView;
    private String urls[],temp[];
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onSharedIntent();
        Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void onSharedIntent(){
        Intent receivedIntent=getIntent();
        String receivedAction=receivedIntent.getAction();

        if(receivedAction.equals(Intent.ACTION_SEND)){
            String url=receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            if(url!=null){

                String b=url;
                int x=url.length();
                for(int i=3;i<x;i++){
                    if(url.substring(i-3,i+1).equals("http")){
                       b=url.substring(i-3,x);
                       break;
                    }
                }
                for(int i=0;i<b.length();i++){
                    if(b.charAt(i)==' ') {
                        b = b.substring(0, i);
                        break;
                    }
                }

                urls=loadArray("Links",SendActivity.this);
                int n=urls.length;
                String s[]=new String[n+1];
                for(int i=1;i<=n;i++){
                    s[i]=urls[i-1];
                }
                s[0]=b;
                saveArray(s,"Links",SendActivity.this);
            }
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
