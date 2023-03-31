package com.edts.tmdroid.ui.tv.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TvListType : Parcelable {
    object Popular : TvListType()
    object TopRated : TvListType()
    object OnTheAir : TvListType()
    object AiringToday : TvListType()
}
