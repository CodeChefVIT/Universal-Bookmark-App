package com.example.bookmark.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private ArrayList<String> urls;
    private Context context;

    Adapter2(Context context, ArrayList<String> urls){
        this.context=context;
        this.urls=urls;
    }

    @NonNull
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new Adapter2.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.ViewHolder holder, final int position) {
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.loadUrl(urls.get(position));

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
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);
        }
    }

}
