package com.bsrakdg.movies.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bsrakdg.movies.BuildConfig
import com.bsrakdg.movies.models.Genre
import com.bsrakdg.movies.utils.Constant

/**
 *  Remote Layer
 */
class MovieServiceClient private constructor() {

    private var moviesService: MoviesApi = MovieService.movieService

    var genres: MutableLiveData<List<Genre>> = MutableLiveData()

    object MovieServiceClientInstance {
        var instance = MovieServiceClient()
    }

    companion object {
        val instance: MovieServiceClient by lazy {
            MovieServiceClientInstance.instance
        }
    }

    fun receiveGenres() {
        // TODO IO Thread needed
        val response = moviesService.getGenres(BuildConfig.API_KEY, Constant.language).execute()
        response.let {
            if (it.code() == 200) {
                it.body()?.let { genresResponse ->
                    genresResponse.genres.let {
                        this.genres.value = genresResponse.genres
                    }
                }
            }
        }
    }

    fun getGenres(): LiveData<List<Genre>> {
        return genres
    }
}