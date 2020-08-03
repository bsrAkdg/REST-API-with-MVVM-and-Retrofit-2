package com.bsrakdg.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bsrakdg.movies.R
import com.bsrakdg.movies.viewmodels.GenresViewModel

class GenresFragment : Fragment() {

    val genresViewModel by viewModels<GenresViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        genresViewModel.receiveGenres()
    }

    fun subscribeObservers() {
        genresViewModel.getGenres().observe(viewLifecycleOwner, Observer { genres ->
            Log.e("DENEME", "" + genres.size)
        })
    }
}