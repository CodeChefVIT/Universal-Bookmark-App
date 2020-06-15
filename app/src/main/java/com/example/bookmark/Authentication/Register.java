package com.example.bookmark.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmark.Backend.ApiHolder;
import com.example.bookmark.Backend.Users;
import com.example.bookmark.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText username, email, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username_input);
        email=findViewById(R.id.email_input);
        password=findViewById(R.id.pass_input);
        register=findViewById(R.id.register);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://ubmcc.herokuapp.com/auth/token/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiHolder apiHolder=retrofit.create(ApiHolder.class);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(apiHolder);
            }
        });

    }
    void registerUser(ApiHolder apiHolder){

        String name=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        String mail=email.getText().toString();

        Users user=new Users(mail, name, pass);
        Call<Users> call=apiHolder.createUser(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Toast.makeText(Register.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
