package com.example.bookmark.Main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;;
import android.widget.Toast;

import com.example.bookmark.Backend.TinyDB;

import java.util.ArrayList;


public class SendActivity extends AppCompatActivity {

    ArrayList<String> urls, res, ent;

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

                TinyDB tinyDB=new TinyDB(getApplicationContext());

                urls =tinyDB.getListString("Links");
                ent =tinyDB.getListString("Entertainment");
                res =tinyDB.getListString("Research");

                String b=getAddress(url);
                ArrayList<String> temp=new ArrayList<>();
                temp.add(b);
                temp.addAll(urls);

                tinyDB.putListString("Links", temp);

                for(int i=0;i<b.length()-3;i++){
                    String k=b.substring(i,i+3);
                    if(k.equals("you")||k.equals("ins")||k.equals("fac")||k.equals("twi")||k.equals("pin")){
                        ArrayList<String> newList=new ArrayList<>();
                        newList.add(b);
                        newList.addAll(ent);
                        tinyDB.putListString("Entertainment", newList);
                        break;
                    }
                    if(k.equals("sta")||k.equals("med")||k.equals("gee")||k.equals("w3s")){
                        ArrayList<String> newList=new ArrayList<>();
                        newList.add(b);
                        newList.addAll(ent);
                        tinyDB.putListString("Research", newList);
                        break;
                    }
                }
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
