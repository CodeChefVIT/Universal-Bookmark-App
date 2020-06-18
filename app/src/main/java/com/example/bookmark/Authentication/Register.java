package com.example.bookmark.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Backend.TinyDB;
import com.example.bookmark.Backend.Users;
import com.example.bookmark.Main.Main;
import com.example.bookmark.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText username, email, password;
    String name, pass, mail;
    Button register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username_input);
        email=findViewById(R.id.email_input);
        password=findViewById(R.id.pass_input);
        register=findViewById(R.id.register);
        progressBar=findViewById(R.id.progress);

        TextView login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        Retrofit createRetrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit loginRetrofit=new  Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/auth/token/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=createRetrofit.create(ApiHolder.class);
        final ApiHolder apiHolder2=loginRetrofit.create(ApiHolder.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                registerUser(apiHolder, apiHolder2);
            }
        });

    }
    void registerUser(final ApiHolder apiHolder, final ApiHolder apiHolder2){

        name=username.getText().toString().trim();
        pass=password.getText().toString().trim();
        mail=email.getText().toString();

        final Users user=new Users(mail, name, pass);
        Call<Users> call=apiHolder.createUser(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.message().equals("Created"))
                    loginUser(apiHolder2);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loginUser(final ApiHolder apiHolder){

        final Users user=new Users(mail, name, pass);
        Call<Users> call=apiHolder.login(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if(response.message().equals("OK")){
                    TinyDB tinyDB=new TinyDB(getApplicationContext());
                    tinyDB.putString("Token", response.body().getAuth_token());
                    Intent intent=new Intent(Register.this, Main.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(Register.this, "User not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
