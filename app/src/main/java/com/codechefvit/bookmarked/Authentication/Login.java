package com.codechefvit.bookmarked.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codechefvit.bookmarked.Backend.ApiHolder;
import com.codechefvit.bookmarked.Backend.TinyDB;
import com.codechefvit.bookmarked.Backend.Users;
import com.codechefvit.bookmarked.Main.Main;
import com.codechefvit.bookmarked.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    String name, pass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username=findViewById(R.id.name_input);
        final TextInputEditText password=findViewById(R.id.pass_input);
        Button login=findViewById(R.id.login);
        TextView skip=findViewById(R.id.skip);
        TextView register=findViewById(R.id.register_here);
        progressBar=findViewById(R.id.progress);

        Retrofit loginRetrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/auth/token/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=loginRetrofit.create(ApiHolder.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=username.getText().toString().trim();
                pass=password.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                loginUser(apiHolder);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Main.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void loginUser(ApiHolder apiHolder){

        final Users user=new Users("", name, pass);
        Call<Users> call=apiHolder.login(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.message().equals("OK")){
                    TinyDB tinyDB=new TinyDB(getApplicationContext());
                    tinyDB.putString("Token", response.body().getAuth_token());
                    Intent intent=new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
