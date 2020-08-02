package com.bsrakdg.movies.api

import com.bsrakdg.movies.api.responses.GetGenresResponse
import com.bsrakdg.movies.api.responses.GetMoviesResponse
import com.bsrakdg.movies.api.responses.GetSearchMoviesResponse
import com.bsrakdg.movies.models.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    // genres
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GetGenresResponse>

    // movies
    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("with_genres") genres: String
    ): Call<GetMoviesResponse>

    // movie detail
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("movie_id") movieId: Int
    ): Call<Movie>

    // search movie
    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Call<GetSearchMoviesResponse>

}