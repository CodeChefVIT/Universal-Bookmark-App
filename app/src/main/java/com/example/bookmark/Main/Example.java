package com.example.bookmark.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Backend.Posts;
import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.R;
import com.example.bookmark.Adapters.Adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Example extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);
        final TinyDB tinyDB=new TinyDB(getApplicationContext());
        final ArrayList<String> addresses=new ArrayList<>();

        Call<List<Posts>> call=apiHolder.getURLs("Token "+tinyDB.getString("Token"));
        call.enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                List<Posts> posts=response.body();
                for(Posts post1:posts) {
                    addresses.add(post1.getUrl());
                }
                tinyDB.putListString("URLs", addresses);
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Toast.makeText(Example.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> urls=tinyDB.getListString("URLs");
        Adapter adapter=new Adapter(this, urls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
