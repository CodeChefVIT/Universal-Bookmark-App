package com.example.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class TitleActivity extends AppCompatActivity {

    ImageView title_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        title_image=findViewById(R.id.title_image);
        title_image.setImageResource(R.drawable.icon);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
