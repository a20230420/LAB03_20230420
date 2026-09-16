package com.example.lab03_20230420.dto;

import com.google.gson.annotations.SerializedName;

//Para obtener datos de la película
public class Movie {
    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
}