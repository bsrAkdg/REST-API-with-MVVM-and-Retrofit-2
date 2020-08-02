package com.bsrakdg.movies.api.responses

import android.os.Parcelable
import com.bsrakdg.movies.models.Genre
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetGenresResponse(
    var genres: List<Genre>
) : Parcelable