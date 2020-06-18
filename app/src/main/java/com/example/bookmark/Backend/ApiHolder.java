package com.example.bookmark.Backend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiHolder {

    @POST("users/")
    Call<Users> createUser(@Body Users user);

    @POST("login")
    Call<Users> login(@Body Users user);

    @POST("add/")
    Call<URL> add(@Header ("Authorization") String header, @Body URL url);

    @GET("add/")
    Call<List<Posts>> getURLs(@Header ("Authorization") String header);

    @GET("deleteall/")
    Call<URL> deleteAll(@Header ("Authorization") String header);
}

