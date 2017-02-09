package com.rajesh.retrofit2sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rajesh.retrofit2sample.rest.request.UserDetails;
import com.rajesh.retrofit2sample.rest.response.UserDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String name;
    private UserDetails userDetails;
    private Button btnGet;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGet = (Button) findViewById(R.id.btnGet);
        btnPost = (Button) findViewById(R.id.btnPost);

        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnGet:

                // Get method by using retrofit and handle response.
                retrofitGetMethod();

                break;

            case R.id.btnPost:

                /*
                *  Post method by using retrofit and handle response.
                *  Sending userDetails pojo class as request body to post method by inserting details.
                *  Getting response with userDetailsResponse pojo class by direct conversion from
                *  Json to pojo class object by using Gson.*/
                retrofitPostMethod();

                break;
        }

    }

    private void retrofitGetMethod(){

        Call<Response> serviceMethod = MainApplication.getInstance().getRetrofitServices().getMethod(name);

        serviceMethod.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {


                // Handle response
                Log.e("Retrofit", "Response: " + response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                // Handle error
                Log.e("Retrofit", "onFailure: " + t.getMessage());


            }
        });
    }

    private void retrofitPostMethod(){

        // Insert details into request body.
        userDetails = new UserDetails();
        userDetails.setUserName("username");
        userDetails.setPassword("password");

        Call<UserDetailsResponse> serviceMethod = MainApplication.getInstance().getRetrofitServices().postMethod(userDetails);

        serviceMethod.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                // Handle response

                Log.e("Retrofit", "UserID: " + response.body().getUserID());
                Log.e("Retrofit", "TokenID: " + response.body().getTokenID());
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

                Log.e("Retrofit", "onFailure: " + t.getMessage());

            }
        });


    }


}
