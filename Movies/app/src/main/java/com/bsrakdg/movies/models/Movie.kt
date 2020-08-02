package com.bsrakdg.movies.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var poster_path: String,
    var backdrop_path: String,
    var overview: String,
    var release_date: String,
    var original_language: String,
    var genres: List<Genre>,
    var title: String,
    var popularity: Int,
    var vote_count: Int,
    var vote_average: Int
) : Parcelable