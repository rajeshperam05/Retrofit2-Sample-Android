package com.rajesh.retrofit2sample.services;

import com.rajesh.retrofit2sample.rest.request.UserDetails;
import com.rajesh.retrofit2sample.rest.response.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by rajesh on 4/5/16.
 */
public interface RetrofitServices {

    @GET("example.mobi/{Name}") // Extension for BASE URL
    Call<Response> getMethod(@Path("Name") String e_mail);

    @POST("example.mobi/")
    Call<UserDetailsResponse> postMethod(@Body UserDetails userDetails);
}
