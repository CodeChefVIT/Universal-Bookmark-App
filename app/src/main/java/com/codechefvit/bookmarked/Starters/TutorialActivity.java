package com.codechefvit.bookmarked.Starters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codechefvit.bookmarked.Authentication.Login;
import com.codechefvit.bookmarked.Backend.TinyDB;
import com.codechefvit.bookmarked.R;

public class TutorialActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        TinyDB tinyDB=new TinyDB(this);
        tinyDB.putBoolean("State", true);
        tinyDB.putInt("View",0);

        button=findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TutorialActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }

}
