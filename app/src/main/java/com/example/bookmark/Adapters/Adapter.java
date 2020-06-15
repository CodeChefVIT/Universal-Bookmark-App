package com.example.bookmark.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.R;
import com.example.bookmark.Backend.TinyDB;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<String> urls;
    private Context context;

    public Adapter(Context context, ArrayList<String> urls){
        this.context=context;
        this.urls=urls;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new Adapter.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, final int position) {
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.loadUrl(urls.get(position));

        holder.goTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(urls.get(position));
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        holder.bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TinyDB tinyDB=new TinyDB(context);
                String b=urls.get(position);
                int flag=0;

                for(int i=0;i<b.length()-3;i++){
                    String k=b.substring(i,i+3);
                    if(k.equals("you")||k.equals("ins")||k.equals("fac")||k.equals("twi")||k.equals("pin")){
                        ArrayList<String> ent=urls;
                        ent.remove(b);
                        tinyDB.putListString("Entertainment", ent);
                        flag=1;
                        break;
                    }
                    else if(k.equals("sta")||k.equals("med")||k.equals("gee")||k.equals("w3s")){
                        ArrayList<String> res=urls;
                        res.remove(b);
                        tinyDB.putListString("Research", res);
                        flag=1;
                        break;
                    }
                }

                if(flag==0){
                    ArrayList<String> mis=urls;
                    mis.remove(b);
                    tinyDB.putListString("Links", mis);
                }

                notifyDataSetChanged();

                tinyDB.putListString("Links", urls);
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        WebView webView;
        ImageView goTo, bin;

        @SuppressLint("SetJavaScriptEnabled")
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView=itemView.findViewById(R.id.webview);
            goTo=itemView.findViewById(R.id.go_to);
            bin=itemView.findViewById(R.id.bin);

            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);
        }
    }

}
