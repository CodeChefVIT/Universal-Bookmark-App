package com.example.bookmark.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.Favicon.Icon;
import com.example.bookmark.Favicon.ImageURL;
import com.example.bookmark.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter3 extends RecyclerView.Adapter<Adapter3.ViewHolder> {

    private ArrayList<String> urls;
    private Context context;

    public Adapter3(Context context, ArrayList<String> urls){
        this.context=context;
        this.urls=urls;
    }

    @NonNull
    @Override
    public Adapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new Adapter3.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter3.ViewHolder holder, final int position) {

        final String url=urls.get(position);
        String site=getWebsite(url);
        holder.website.setText(site);
        holder.link.setText(url);

        setImage(holder.imageView, url);

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(urls.get(position));
                Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu=new PopupMenu(context, holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.menu3, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.addEnt:
                                urls.set(position, url+"instagram");
                                break;
                            case R.id.addRes:
                                urls.set(position, url+"medium");
                                break;
                        }
                        TinyDB tinyDB=new TinyDB(context);
                        tinyDB.putListString("Links", urls);
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView bin;
        TextView website, link;

        @SuppressLint("SetJavaScriptEnabled")
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.favicon);
            bin=itemView.findViewById(R.id.bin);
            website=itemView.findViewById(R.id.website);
            link=itemView.findViewById(R.id.link);
        }
    }
    private String getWebsite(String url){
        int i, flag=0;
        String site="";
        if(url.contains("youtu"))
            return "Youtube";
        if(url.contains("stack"))
            return "StackOverflow";
        if(url.contains("www")) {
            for (i = 0; i < url.length() - 1; i++) {
                if (url.charAt(i) == '.')
                    flag++;
                if (flag == 1)
                    site = site + url.charAt(i + 1);
                if (flag == 2) {
                    String firstLetter = site.substring(0, 1);
                    site = firstLetter.toUpperCase() + site.substring(1, site.length() - 1);
                    break;
                }
            }
	    return site;
        }
        else{
            for(i=0;i<url.length()-2;i++){
                if(url.substring(i,i+2).equals("//"))
                    return url.substring(i+2,i+3).toUpperCase()+ url.substring(i+3, url.indexOf('.'));
            }
        }
        return site;
    }

    private void setImage(final ImageView image, String url){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://besticon-demo.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHolder apiHolder=retrofit.create(ApiHolder.class);
        Call<ImageURL> call=apiHolder.getFavicon("allicons.json?url="+url);
        call.enqueue(new Callback<ImageURL>() {
            @Override
            public void onResponse(Call<ImageURL> call, Response<ImageURL> response) {
                if(!response.message().equals("Not Found")) {
                    List<Icon> URLs = response.body().getIcons();
                    Picasso.get().load(URLs.get(0).getUrl()).into(image);
                }
            }

            @Override
            public void onFailure(Call<ImageURL> call, Throwable t) {

            }
        });

    }
}
