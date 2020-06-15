package com.example.bookmark.Backend;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiHolder {

    @POST("login")
    Call<Users> createUser(@Body Users user);
}
