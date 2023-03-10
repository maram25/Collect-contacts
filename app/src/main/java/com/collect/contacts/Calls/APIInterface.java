package com.collect.contacts.Calls;


import com.collect.contacts.Models.ContactModel;
import com.collect.contacts.Models.LoginModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface
APIInterface {


    //login
    @FormUrlEncoded
    @POST("api/customer/login")
    @Headers({"Accept: application/json"})
    Call<LoginModel> Login(
            @Field("lange") String Locale,
            @Field("phone") String phone,
            @Field("password") String password
    );


    @POST("api/acceptance/payment_keys")
    @Headers({"Accept:application/json"})
    Call<ContactModel> ContactList(
            @Body HashMap<String, Object> param);




}
