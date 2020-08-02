package com.bsrakdg.movies.api.responses

import android.os.Parcelable
import com.bsrakdg.movies.models.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetMoviesResponse(
    var page: Int,
    var results: List<Movie>,
    var total_results: Int,
    var total_pages: Int

) : Parcelable