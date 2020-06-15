package com.example.bookmark.Starters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.Main.SampleActivity;
import com.example.bookmark.R;

public class TutorialActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        TinyDB tinyDB=new TinyDB(this);
        tinyDB.putBoolean("State", false);

        button=findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TutorialActivity.this, SampleActivity.class);
                startActivity(intent);
            }
        });

    }

}
