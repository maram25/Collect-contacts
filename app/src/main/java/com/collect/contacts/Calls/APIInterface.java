package com.collect.contacts.Calls;


import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.GroupsModel;
import com.collect.contacts.Models.LoginModel;
import com.collect.contacts.Models.UserProfileModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface
APIInterface {


    //login
    @FormUrlEncoded
    @POST("api/user/login")
    @Headers({"Accept: application/json"})
    Call<LoginModel> Login(
            @Field("email") String email,
            @Field("password") String password
    );

 //sendSMS
    @FormUrlEncoded
    @POST("api/user/phones/sms/send")
    @Headers({"Accept: application/json"})
    Call<ContactModel> SendSMS(
            @Header("authorization") String Token,
            @Field("sender_id") String sender_id,
            @Field("phones[]") List<String> phones,
            @Field("msg") String msg
    );
 //sendSMS

    @FormUrlEncoded
    @POST("api/user/group/store")
    @Headers({"Accept: application/json"})
    Call<GroupsModel> GroupStore(
            @Header("authorization") String Token,
            @Field("sender_id") String sender_id,
            @Field("phones[]") List<String> phones,
            @Field("msg") String msg
    );


    //logout
    @POST("api/user/logout")
    @Headers({"Accept: application/json"})
    Call<LoginModel> logout(
            @Header("authorization") String Token);




    @GET("api/user/profile")
    @Headers({"Accept: application/json"})
    Call<UserProfileModel> UserProfile(
            @Header("Authorization") String Token
    );
    @GET("api/user/groups")
    @Headers({"Accept: application/json"})
    Call<GroupsModel> GetGroups(
            @Header("Authorization") String Token
    );


}
