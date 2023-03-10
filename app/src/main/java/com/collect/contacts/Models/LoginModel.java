package com.collect.contacts.Models;

import com.google.gson.annotations.SerializedName;

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
            @SerializedName("first_name")
            private String first_name;
            @SerializedName("last_name")
            private String last_name;
            @SerializedName("email")
            private String email;
            @SerializedName("phone")
            private String phone;

            @SerializedName("access_token")
            private String access_token;


                public int getDomain_id() {
                    return domain_id;
                }

                public void setDomain_id(int domain_id) {
                    this.domain_id = domain_id;
                }

                public String getFirst_name() {
                    return first_name;
                }

                public void setFirst_name(String first_name) {
                    this.first_name = first_name;
                }

                public String getLast_name() {
                    return last_name;
                }

                public void setLast_name(String last_name) {
                    this.last_name = last_name;
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

                public String getAccess_token() {
                    return access_token;
                }

                public void setAccess_token(String access_token) {
                    this.access_token = access_token;
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