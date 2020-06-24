package com.codechefvit.bookmarked.Backend;

import com.codechefvit.bookmarked.Favicon.ImageURL;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

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

    @GET
    Call<ImageURL> getFavicon(@Url String url);
}

