package com.edts.tmdroid.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class PersonListType : Parcelable {
    object Popular : PersonListType()
    data class SearchPeople(
        val query: String,
    ) : PersonListType()
}
