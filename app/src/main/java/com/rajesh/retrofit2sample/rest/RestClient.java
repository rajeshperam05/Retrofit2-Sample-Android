package com.rajesh.retrofit2sample.rest;


import android.util.Base64;

import com.rajesh.retrofit2sample.services.RetrofitServices;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ramya on 23/12/15.
 */
public class RestClient {

    private static final String BASE_URL = "http://000.000.8080/retrofit/api/"; // replace with your url

    private RetrofitServices retrofitServices; // Services interface


    /*
    * We can use Gson in retrofit to convert Json response directly in java pojo class.
    * Headers are common to every request*/
    public RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient())
                .build();

        retrofitServices = retrofit.create(RetrofitServices.class);
    }

    public RetrofitServices getRetrofitServices() {
        return retrofitServices;
    }

    // build OkHttp client
    private OkHttpClient buildOkHttpClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Accept-Language", "en-gb")
                        .header("Authorization", getCustomAuth())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        httpClient.addInterceptor(logging);

        return httpClient.build();
    }


    // If the service has security
    private String getCustomAuth() {

        final String basicAuth = "Basic " + Base64.encodeToString("username:password".getBytes(), Base64.NO_WRAP);
//        String basicAuth = "Basic " + Preferences.token;

        return basicAuth;

    }

}
