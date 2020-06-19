package com.example.bookmark.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Favicon.Icon;
import com.example.bookmark.Favicon.ImageURL;
import com.example.bookmark.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView=findViewById(R.id.favicon);
        setImage(imageView, "https://stackoverflow.com/");
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
                List<Icon> URLs=response.body().getIcons();
                Picasso.get().load(URLs.get(0).getUrl()).into(image);
            }

            @Override
            public void onFailure(Call<ImageURL> call, Throwable t) {
                Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
