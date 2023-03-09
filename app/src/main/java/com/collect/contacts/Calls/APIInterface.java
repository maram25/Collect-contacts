package com.collect.contacts.Calls;


import com.collect.contacts.ContactModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface
APIInterface {


    @FormUrlEncoded
    @POST("api/customer/login")
    @Headers({"Accept: application/json"})
    Call<ContactModel> Login(
            @Field("locale") String Locale,
            @Field("identify") String identify,
            @Field("password") String password,
            @Field("token_device") String token,
            @Field("type_device") int type_device
    );

    @POST("api/acceptance/payment_keys")
    @Headers({"Accept:application/json"})
    Call<ContactModel> ContactList(
            @Body HashMap<String, Object> param);




}
