package com.example.lab03_20230420.services;

import com.example.lab03_20230420.dto.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbService {
    // La URL base será https://www.omdbapi.com/
    @GET("/")
    Call<Movie> getMovieById(@Query("i") String imdbId, @Query("apikey") String apiKey);
}