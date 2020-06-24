package com.codechefvit.bookmarked.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codechefvit.bookmarked.Backend.ApiHolder;
import com.codechefvit.bookmarked.Favicon.Icon;
import com.codechefvit.bookmarked.Favicon.ImageURL;
import com.codechefvit.bookmarked.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        imageView=findViewById(R.id.favicon);
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage(imageView, editText.getText().toString().trim());
            }
        });

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
                Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
