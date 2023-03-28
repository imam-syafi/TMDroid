package com.edts.tmdroid.data.common

import com.google.gson.annotations.SerializedName

enum class Gender {
    @SerializedName("0")
    NotSpecified {
        override fun toString(): String = "Not specified"
    },

    @SerializedName("1")
    Female,

    @SerializedName("2")
    Male,

    @SerializedName("3")
    NonBinary {
        override fun toString(): String = "Non binary"
    },
}
