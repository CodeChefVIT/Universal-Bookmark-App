package com.example.bookmark.Main;

import android.content.Intent;
import android.os.Bundle;

import com.example.bookmark.Authentication.Login;
import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tinyDB=new TinyDB(getApplicationContext());
        categorize();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        TinyDB tinyDB=new TinyDB(getApplicationContext());
        tinyDB.putBoolean("Flag", false);
    }

    private void categorize(){
        ArrayList<String> links=tinyDB.getListString("Links");
        ArrayList<String> ent=new ArrayList<>();
        ArrayList<String> res=new ArrayList<>();
        for(String link:links){
            for(int i=0;i<link.length()-3;i++){
                String k=link.substring(i,i+3);
                if(k.equals("you")||k.equals("ins")||k.equals("fac")||k.equals("twi")||k.equals("pin")){
                    ent.add(link);
                    break;
                }
                if(k.equals("sta")||k.equals("med")||k.equals("gee")||k.equals("w3s")){
                    res.add(link);
                    break;
                }
            }
        }
        tinyDB.putListString("Entertainment", ent);
        tinyDB.putListString("Research", res);
    }

}
