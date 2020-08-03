package com.bsrakdg.movies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bsrakdg.movies.models.Genre
import com.bsrakdg.movies.repositories.GenresRepository

class GenresViewModel : ViewModel() {
    private var genresRepository: GenresRepository = GenresRepository.instance

    fun getGenres(): LiveData<List<Genre>> {
        return genresRepository.getGenres()
    }

    fun receiveGenres() {
        genresRepository.receiveGenres()
    }
}