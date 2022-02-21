package com.convergence.travelarrangement;

import com.convergence.travelarrangement.model.GetListFormsModel;
import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.SetListFormsModel;
import com.convergence.travelarrangement.model.SubmitFormModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("travel/submitform")
    @FormUrlEncoded
    Call<SubmitFormModel> submitform(@Field("name") String name,
                                     @Field("nik") String nik,
                                     @Field("division") String division,
                                     @Field("phonenumber") String phonenumber,
                                     @Field("email") String email,
                                     @Field("travelreason") String travelreason,
                                     @Field("fromcity") String fromcity,
                                     @Field("tocity") String tocity,
                                     @Field("dates") String dates,
                                     @Field("duration") String duration,
                                     @Field("urgent") String urgent);
    @GET("travel/getlistform")
    Call<GetListFormsModel> getlistform();
    @GET("travel/getlistformpendingkaryawan")
    Call<GetListFormsModel> getlistformpendingkaryawan();
    @GET("travel/getlistformdonekaryawan")
    Call<GetListFormsModel> getlistformdonekaryawan();
    @GET("travel/getlistformadmin")
    Call<GetListFormsModel> getlistformadmin();
    @POST("travel/setFormAdmin")
    @FormUrlEncoded
    Call<SetListFormsModel> setFormAdmin(@Field("id_ticketarr") String id_ticketarr,
                                         @Field("status") String status);
}
