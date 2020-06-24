package com.codechefvit.bookmarked.Main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;;
import android.widget.Toast;

import com.codechefvit.bookmarked.Backend.TinyDB;

import java.util.ArrayList;


public class SendActivity extends AppCompatActivity {

    ArrayList<String> urls;
    TinyDB tinyDB;


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

        assert receivedAction != null;
        if(receivedAction.equals(Intent.ACTION_SEND)){
            String url=receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
            if(url!=null){

                tinyDB=new TinyDB(getApplicationContext());
                urls=tinyDB.getListString("Links");

                String b=getAddress(url);
                ArrayList<String> temp=new ArrayList<>();
                temp.add(b);
                temp.addAll(urls);

                tinyDB.putListString("Links", temp);


            }
        }
    }

    public String getAddress(String url){
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
        return b;
    }

}
