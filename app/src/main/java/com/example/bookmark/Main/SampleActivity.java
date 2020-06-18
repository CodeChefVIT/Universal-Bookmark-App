package com.example.bookmark.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bookmark.Adapters.Adapter;
import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Backend.Posts;
import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> addresses=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TinyDB tinyDB=new TinyDB(getApplicationContext());

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);

        Call<List<Posts>> call=apiHolder.getURLs("Token "+tinyDB.getString("Token"));
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                List<Posts> posts=response.body();
                for(Posts post1:posts) {
                    addresses.add(post1.getUrl());
                    Toast.makeText(SampleActivity.this, addresses.get(0), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(SampleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //final Adapter adapter=new Adapter(getApplicationContext(), urls);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(SampleActivity.this, urls.get(0), Toast.LENGTH_SHORT).show();

        swipeRefreshLayout=findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
