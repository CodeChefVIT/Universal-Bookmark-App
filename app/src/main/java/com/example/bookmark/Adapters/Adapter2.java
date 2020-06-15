package com.example.bookmark.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.R;

import java.util.ArrayList;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private ArrayList<String> urls;
    private Context context;

    public Adapter2(Context context, ArrayList<String> urls){
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

        String url=urls.get(position);
        String site=getWebsite(url);
        holder.website.setText(site);
        holder.link.setText(url);

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
        ImageView bin;
        TextView website, link;

        @SuppressLint("SetJavaScriptEnabled")
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            webView=itemView.findViewById(R.id.webview);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            WebSettings settings = webView.getSettings();
            settings.setDomStorageEnabled(true);

            bin=itemView.findViewById(R.id.bin);
            website=itemView.findViewById(R.id.website);
            link=itemView.findViewById(R.id.link);
        }
    }
    private String getWebsite(String url){
        int i, flag=0;
        String site="";
        for(i=0;i<url.length()-1;i++){
            if(url.charAt(i)=='.')
                flag++;
            if(flag==1)
                site=site+url.charAt(i+1);
            if(flag==2) {
                String firstLetter=site.substring(0, 1);
                site=firstLetter.toUpperCase()+site.substring(1, site.length()-1);
                break;
            }
        }
        return site;
    }
}
