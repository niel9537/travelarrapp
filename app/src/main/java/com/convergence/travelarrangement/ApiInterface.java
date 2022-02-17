package com.convergence.travelarrangement;

import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("auth/login")
    @FormUrlEncoded
    Call<LoginModel> login(@Field("username") String username,
                           @Field("password") String password);
    @POST("main/getprofile")
    @FormUrlEncoded
    Call<ProfileModel> getProfile(@Field("username") String username);

}
