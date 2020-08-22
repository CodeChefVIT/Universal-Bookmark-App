package com.codechefvit.bookmarked.CategoryFragments.home;

import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codechefvit.bookmarked.Adapters.Adapter;
import com.codechefvit.bookmarked.Adapters.Adapter2;
import com.codechefvit.bookmarked.Authentication.Login;
import com.codechefvit.bookmarked.Backend.ApiHolder;
import com.codechefvit.bookmarked.Backend.Posts;
import com.codechefvit.bookmarked.Backend.URL;
import com.codechefvit.bookmarked.Main.Main;
import com.codechefvit.bookmarked.R;
import com.codechefvit.bookmarked.Backend.TinyDB;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private TinyDB tinyDB;
    private int flag;
    private ArrayList<String> urls;
    private Adapter adapter;
    private Adapter2 adapter2;
    private ArrayList<String> links;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        tinyDB = new TinyDB(getContext());
        urls=new ArrayList<>();
        links=tinyDB.getListString("Links");
        for(String link:links)
            if(link.contains("stack")||link.contains("github")||link.contains("geek")||link.contains("news")||link.contains("medium")||link.contains("blog"))
                urls.add(link);

        final View root = inflater.inflate(R.layout.activity_main, container, false);

        TextView reminder=root.findViewById(R.id.blank_screen);

        if(urls.size()==0)
            reminder.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.recyclerView);

        flag=tinyDB.getInt("View");
        if(flag==1){
            adapter2=new Adapter2(getContext(), urls);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else{
            adapter=new Adapter(getContext(), urls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Toolbar toolbar=root.findViewById(R.id.bar);

        if(tinyDB.getString("Token").length()>10)
            toolbar.inflateMenu(R.menu.menu);
        else
            toolbar.inflateMenu(R.menu.menu2);;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.listChange : changeView();
                        break;

                    case R.id.signOut : signOut();
                        break;

                    case R.id.sync : sync();
                        break;

                    case R.id.download : download();
                        break;
                }
                return false;
            }
        });

        swipeRefreshLayout = root.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(getContext(), Main.class);
                startActivity(intent);
                getActivity().finish();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;

    }

    private String deletedURL=null;

    private ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position=viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.RIGHT:
                    deletedURL=urls.get(position);
                    urls.remove(position);
                    links.remove(position);
                    if(flag==1) adapter2.notifyItemRemoved(position);
                    else adapter.notifyItemRemoved(position);
                    tinyDB.putListString("Links", links);
                    Snackbar.make(recyclerView, deletedURL, BaseTransientBottomBar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    urls.add(position, deletedURL);
                                    links.add(position, deletedURL);
                                    if(flag==1) adapter2.notifyItemRemoved(position);
                                    else adapter.notifyItemRemoved(position);
                                    tinyDB.putListString("Links", links);
                                }
                            }).show();
                    break;
                case ItemTouchHelper.LEFT:
                    Uri uri = Uri.parse(urls.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                    .addSwipeRightActionIcon(R.drawable.delete)
                    .addSwipeLeftActionIcon(R.drawable.open)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void changeView(){
        if(flag==0){
            Adapter2 adapter2=new Adapter2(getContext(), urls);
            recyclerView.setAdapter(adapter2);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tinyDB.putInt("View", 1);
            flag=1;
        }
        else{
            Adapter adapter=new Adapter(getContext(), urls);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            tinyDB.putInt("View", 0);
            flag=0;
        }
    }

    private void signOut(){
        tinyDB.putString("Token", " ");
        Intent intent=new Intent(getContext(), Login.class);
        startActivity(intent);
    }

    private void download(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);
        final TinyDB tinyDB=new TinyDB(getContext());
        final ArrayList<String> addresses=new ArrayList<>();

        Call<List<Posts>> call=apiHolder.getURLs("Token "+tinyDB.getString("Token"));
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                List<Posts> posts=response.body();
                for(Posts post1:posts) {
                    addresses.add(post1.getUrl());
                }
                tinyDB.putListString("Links", addresses);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getContext(), Main.class);
                startActivity(intent);
            }
        }, 1000);
    }

    private void sync(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);
        final TinyDB tinyDB=new TinyDB(getContext());

        Call<URL> call=apiHolder.deleteAll("Token "+tinyDB.getString("Token"));
        call.enqueue(new Callback<URL>() {
            @Override
            public void onResponse(Call<URL> call, Response<URL> response) {

            }

            @Override
            public void onFailure(Call<URL> call, Throwable t) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> links;
                links=tinyDB.getListString("Links");
                for(String b:links)
                    uploadURL(b);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Synced", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void uploadURL(String b){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);

        URL url=new URL(b);
        Call<URL> call=apiHolder.add("Token "+tinyDB.getString("Token"), url);
        call.enqueue(new Callback<URL>() {
            @Override
            public void onResponse(Call<URL> call, Response<URL> response) {

            }

            @Override
            public void onFailure(Call<URL> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}