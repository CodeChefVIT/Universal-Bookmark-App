package com.example.bookmark.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookmark.Main;
import com.example.bookmark.MyAdapter;
import com.example.bookmark.R;

public class NotificationsFragment extends Fragment {

    private String urls[];
    private RecyclerView recyclerView, recyclerview2;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.activity_main, container, false);

        urls=loadArray("Links", getContext());

        if(urls.length!=0){

            recyclerView=root.findViewById(R.id.recyclerView);
            final MyAdapter myAdapter=new MyAdapter(getContext(),urls);
            recyclerView.setAdapter(myAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            myAdapter.onItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Uri uri=Uri.parse(urls[position]);
                    Intent intent=new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            myAdapter.onItemClickListener2(new MyAdapter.OnItemClickListener2() {
                @Override
                public void onItemClick2(int position) {

                    urls=loadArray("Links",getContext());
                    if(urls.length>1)
                    {
                        for(int i=position;i<urls.length-1;i++){
                            urls[i]=urls[i+1];
                        }
                        String temp[]=new String[urls.length-1];
                        for(int i=0;i<urls.length-1;i++){
                            temp[i]=urls[i];
                        }
                        saveArray(temp,"Links",getContext());
                        urls=loadArray("Links",getContext());
                        Intent intent=new Intent(getContext(), Main.class);
                        startActivity(intent);
                    }
                    else{
                        String a[]=new String[0];
                        saveArray(a,"Links",getContext());
                        Intent intent=new Intent(getContext(), Main.class);
                        startActivity(intent);
                    }

                }
            });

        }
        else{
            View root2 = inflater.inflate(R.layout.activity_main2, container, false);
            return root2;
        }

        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                urls=loadArray("Links",getContext());
                Intent intent=new Intent(getContext(), Main.class);
                startActivity(intent);
                pullToRefresh.setRefreshing(false);
            }
        });

        return root;
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