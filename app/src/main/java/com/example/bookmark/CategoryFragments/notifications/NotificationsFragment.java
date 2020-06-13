package com.example.bookmark.CategoryFragments.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bookmark.Adapters.Adapter;
import com.example.bookmark.Main.Main;
import com.example.bookmark.R;
import com.example.bookmark.Backend.TinyDB;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final TinyDB tinyDB=new TinyDB(getContext());
        final ArrayList<String> urls = tinyDB.getListString("Links");

        if(urls.size()!=0) {

            final View root = inflater.inflate(R.layout.activity_sample, container, false);

            final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
            final Adapter adapter = new Adapter(getContext(), urls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
}