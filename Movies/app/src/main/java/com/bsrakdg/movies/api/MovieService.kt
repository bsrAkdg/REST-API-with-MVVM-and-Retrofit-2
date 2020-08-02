package com.bsrakdg.movies.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieService {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()
                )
            )
            .baseUrl(BASE_URL)
            .build()
    }

    val movieService: MoviesApi by lazy {
        retrofit().create(MoviesApi::class.java)
    }
}