package com.convergence.travelarrangement;

import com.convergence.travelarrangement.model.GetListFormsModel;
import com.convergence.travelarrangement.model.LoginModel;
import com.convergence.travelarrangement.model.ProfileModel;
import com.convergence.travelarrangement.model.RegisterModel;
import com.convergence.travelarrangement.model.SetListFormsModel;
import com.convergence.travelarrangement.model.SubmitFormModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @POST("auth/login")
    @FormUrlEncoded
    Call<LoginModel> login(@Field("username") String username,
                           @Field("password") String password);
    @POST("user/registrasi")
    @FormUrlEncoded
    Call<RegisterModel> registrasi(@Field("nik") String nik,
                                   @Field("name") String name,
                                   @Field("email") String email,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("division") String division,
                                   @Field("role") String role);
    @POST("main/getprofile")
    @FormUrlEncoded
    Call<ProfileModel> getProfile(@Field("username") String username);
    @Multipart
    @POST("travel/submitform")
    Call<SubmitFormModel> submitform(@Part MultipartBody.Part transport,
                                     @Part MultipartBody.Part hotel,
                                     @Part("name") RequestBody name,
                                     @Part("nik") RequestBody nik,
                                     @Part("division") RequestBody division,
                                     @Part("phonenumber") RequestBody phonenumber,
                                     @Part("email") RequestBody email,
                                     @Part("travelreason") RequestBody travelreason,
                                     @Part("fromcity") RequestBody fromcity,
                                     @Part("tocity") RequestBody tocity,
                                     @Part("dates") RequestBody dates,
                                     @Part("duration") RequestBody duration,
                                     @Part("urgent") RequestBody urgent);
    @GET("travel/getlistform")
    Call<GetListFormsModel> getlistform();
    @GET("travel/getlistformpendingkaryawan")
    Call<GetListFormsModel> getlistformpendingkaryawan();
    @GET("travel/getlistformdonekaryawan")
    Call<GetListFormsModel> getlistformdonekaryawan();
    @GET("travel/getlistformadmin")
    Call<GetListFormsModel> getlistformadmin();
    @GET("travel/getlistformpendingadmin")
    Call<GetListFormsModel> getlistformpendingadmin();
    @GET("travel/getlistformmanajer")
    Call<GetListFormsModel> getlistformmanajer();
    @GET("travel/getlistformpendingmanajer")
    Call<GetListFormsModel> getlistformpendingmanajer();
    @GET("travel/getlistformfinance")
    Call<GetListFormsModel> getlistformfinance();
    @GET("travel/getlistformpendingfinance")
    Call<GetListFormsModel> getlistformpendingfinance();
    @POST("travel/setFormAdminManajerDiv")
    @FormUrlEncoded
    Call<SetListFormsModel> setFormAdminManajerDiv(@Field("id_ticketarr") String id_ticketarr,
                                         @Field("status") String status,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("role") String role);
    @GET("travel/getlistformmanajerdiv")
    Call<GetListFormsModel> getlistformmanajerdiv();
    @GET("travel/getlistformpendingmanajerdiv")
    Call<GetListFormsModel> getlistformpendingmanajerdiv();
    @GET("travel/getlistformdonemanajerdiv")
    Call<GetListFormsModel> getlistformdonemanajerdiv();
    @POST("travel/setFormAdmin")
    @FormUrlEncoded
    Call<SetListFormsModel> setFormAdmin(@Field("id_ticketarr") String id_ticketarr,
                                         @Field("status") String status,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("role") String role);
    @POST("travel/setFormAdminBudget")
    @FormUrlEncoded
    Call<SetListFormsModel> setFormAdminBudget(@Field("id_ticketarr") String id_ticketarr,
                                               @Field("status") String status,
                                               @Field("budget") String budget,
                                               @Field("name") String name,
                                               @Field("email") String email,
                                               @Field("role") String role);
    @Multipart
    @POST("travel/upload")
    Call<String> uploadImage(
            @Part MultipartBody.Part file, @Part("transport") RequestBody name);

}
