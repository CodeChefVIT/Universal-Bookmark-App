package com.example.bookmark.CategoryFragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookmark.Adapters.Adapter;
import com.example.bookmark.Adapters.Adapter2;
import com.example.bookmark.Main.Main;
import com.example.bookmark.R;
import com.example.bookmark.Backend.TinyDB;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private int flag=0;
    private ArrayList<String> urls;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final TinyDB tinyDB=new TinyDB(getContext());
        urls = tinyDB.getListString("Links");

        if(urls.size()!=0) {

            final View root = inflater.inflate(R.layout.activity_main, container, false);

            recyclerView = root.findViewById(R.id.recyclerView);
            final Adapter adapter = new Adapter(getContext(), urls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            final ImageButton change_list=root.findViewById(R.id.change_list);
            change_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeView();
                }
            });

            swipeRefreshLayout = root.findViewById(R.id.refresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Intent intent = new Intent(getContext(), Main.class);
                    startActivity(intent);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            return root;

        }else{
            final View root = inflater.inflate(R.layout.blank_screen, container, false);
            swipeRefreshLayout = root.findViewById(R.id.refresh);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Intent intent = new Intent(getContext(), Main.class);
                    startActivity(intent);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            return root;
        }
    }
    private void changeView(){
        if(flag==0){
            Adapter2 adapter2=new Adapter2(getContext(), urls);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            flag=1;
        }
        else{
            Adapter adapter=new Adapter(getContext(), urls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            flag=0;
        }
    }
}