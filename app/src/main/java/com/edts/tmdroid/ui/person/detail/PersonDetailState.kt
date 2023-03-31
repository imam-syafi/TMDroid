package com.edts.tmdroid.ui.person.detail

import com.edts.tmdroid.ui.model.PersonDetail

data class PersonDetailState(
    val isLoading: Boolean,
    val personDetail: PersonDetail?,
)
