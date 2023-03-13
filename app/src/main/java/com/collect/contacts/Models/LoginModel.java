package com.collect.contacts.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginModel {

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @SerializedName("data")
    private Data data;


    public class Data{

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        @SerializedName("user")
        private User user;

        public class User {

            public Original getOriginal() {
                return original;
            }

            public void setOriginal(Original original) {
                this.original = original;
            }

            @SerializedName("original")
            private Original original;

            public class Original {

                @SerializedName("domain_id")
                private int domain_id;
                @SerializedName("name")
                private String name;
                @SerializedName("email")
                private String email;
                @SerializedName("phone")
                private String phone;
                @SerializedName("balance")
                private String balance;

                public int getDomain_id() {
                    return domain_id;
                }

                public void setDomain_id(int domain_id) {
                    this.domain_id = domain_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getBalance() {
                    return balance;
                }

                public void setBalance(String balance) {
                    this.balance = balance;
                }

                public String getAccess_token() {
                    return access_token;
                }

                public void setAccess_token(String access_token) {
                    this.access_token = access_token;
                }

                public List<String> getTelephone_networks() {
                    return telephone_networks;
                }

                public void setTelephone_networks(List<String> telephone_networks) {
                    this.telephone_networks = telephone_networks;
                }

                @SerializedName("access_token")
                private String access_token;
                @SerializedName("telephone_networks")
                List<String> telephone_networks = new ArrayList<>();


                @SerializedName("sender_ids")
                List<IDs> sender_ids = new ArrayList<>();

                public List<IDs> getSender_ids() {
                    return sender_ids;
                }

                public void setSender_ids(List<IDs> sender_ids) {
                    this.sender_ids = sender_ids;
                }

                public class IDs {
                    @SerializedName("name")
                    private String name;

                    public String getName() {

                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

        }

        }

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}