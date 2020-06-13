package com.example.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String urls[];
    Context context;
    private OnItemClickListener mListener;
    private OnItemClickListener2 mListener2;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface OnItemClickListener2{
        void onItemClick2(int position);
    }

    public void onItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public void onItemClickListener2(OnItemClickListener2 listener){
        mListener2=listener;
    }

    public MyAdapter(Context ct, String links[]){
        context=ct;
        urls=links;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view, mListener, mListener2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.webview.setWebViewClient(new WebViewClient());
        holder.webview.loadUrl(urls[position]);
        holder.go_to.setImageResource(R.drawable.go_to);
        holder.bin.setImageResource(R.drawable.bin);
    }

    @Override
    public int getItemCount() {
        return urls.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        WebView webview;
        ImageView go_to, bin;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener, final OnItemClickListener2 listener2){
            super(itemView);
            webview=itemView.findViewById(R.id.webview);
            go_to=itemView.findViewById(R.id.go_to);
            bin=itemView.findViewById(R.id.bin);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webview.getSettings();
            settings.setDomStorageEnabled(true);

            go_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

            bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick2((getAdapterPosition()));
                }
            });


        }

    }
}
