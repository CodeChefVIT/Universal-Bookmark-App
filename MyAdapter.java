package com.example.bookmark;

import android.content.Context;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String urls[];
    Context context;

    public MyAdapter(Context ct, String links[]){
        context=ct;
        urls=links;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.webview.setWebViewClient(new WebViewClient());
        holder.webview.loadUrl(urls[position]);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        WebView webview;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            webview=itemView.findViewById(R.id.webview);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webview.getSettings();
            settings.setDomStorageEnabled(true);
        }

    }
}
