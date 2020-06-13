package com.example.bookmark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private Context context;
    String urls[];

    public MyAdapter2(Context context, String urls[] ){
        this.context=context;
        this.urls=urls;
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.bookmark, parent, false);
        return new MyAdapter2.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(urls[position]);
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        holder.bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urls=loadArray("Links",context);
                if(urls.length>1)
                {
                    for(int i=position;i<urls.length-1;i++){
                        urls[i]=urls[i+1];
                    }
                    String temp[]=new String[urls.length-1];
                    for(int i=0;i<urls.length-1;i++){
                        temp[i]=urls[i];
                    }
                    saveArray(temp,"Links",context);
                    Intent intent=new Intent(context, Main.class);
                    context.startActivity(intent);
                }
                else{
                    String a[]=new String[0];
                    saveArray(a,"Links",context);
                    Intent intent=new Intent(context, Main.class);
                    context.startActivity(intent);
                }
            }
        });

        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.loadUrl(urls[position]);
        holder.bin.setImageResource(R.drawable.bin);

        String c=urls[position];
        String b="";
        int x=c.length();
        for(int i=3;i<x;i++){
            if(c.substring(i-3,i+1).equals("http")){
                b=c.substring(i-3,x);
                break;
            }
        }
        holder.link.setText(b);
        int k=0, i, j;
        for(i=0;i<b.length();i++){
            if(b.charAt(i)=='.'){
                k++;
            }
            if(k==2)
                break;
        }
        for(j=0;j<b.length();j++){
            if(b.charAt(j)=='.')
                break;
        }
        String site=b.substring(j+1,i);
        holder.website.setText(site);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView website, link;
        ImageView bin;
        WebView webView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            website=itemView.findViewById(R.id.website);
            link=itemView.findViewById(R.id.link);
            bin=itemView.findViewById(R.id.bin);
            webView= itemView.findViewById(R.id.webview);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);
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
