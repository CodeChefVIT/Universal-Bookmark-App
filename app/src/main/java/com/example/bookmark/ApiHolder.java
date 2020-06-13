package com.example.bookmark;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiHolder {

    @POST("users")
    Call<Users> createUser(@Body Users user);
}
