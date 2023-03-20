package com.collect.contacts.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StaticPagesModel {


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @SerializedName("data")
    private Data data;

    public class Data {


        public List<Data.pages> getPages() {
            return pages;
        }

        public void setPages(List<Data.pages> pages) {
            this.pages = pages;
        }

        @SerializedName("pages")
        List<Data.pages> pages = new ArrayList<>();

        public class pages {

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            @SerializedName("id")
            private int id;


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @SerializedName("title")
            private String  title;
            @SerializedName("content")
            private String  content;

        }


    }














}
