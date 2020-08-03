package com.bsrakdg.movies.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bsrakdg.movies.api.MovieServiceClient
import com.bsrakdg.movies.models.Genre

/**
 *  Repository Layer
 */
class GenresRepository private constructor() {
    private var moviesServiceClient = MovieServiceClient.instance
    private var genresRepository: GenresRepository = instance

    var mediatorLiveDataGenres: MediatorLiveData<List<Genre>> = MediatorLiveData()

    init {
        initGenres()
    }

    private object GenresRepositoryInstance {
        val instance = GenresRepository()
    }

    // https://medium.com/swlh/singleton-class-in-kotlin-c3398e7fd76b
    companion object {
        val instance: GenresRepository by lazy {
            GenresRepositoryInstance.instance
        }
    }

    private fun initGenres() {
        mediatorLiveDataGenres.addSource(moviesServiceClient.genres) { value ->
            mediatorLiveDataGenres.value = value
            mediatorLiveDataGenres.removeSource(moviesServiceClient.genres)
        }
    }

    fun getGenres(): LiveData<List<Genre>> {
        return mediatorLiveDataGenres
    }

    fun receiveGenres() {
        moviesServiceClient.receiveGenres()
    }

}