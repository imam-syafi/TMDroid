package com.edts.tmdroid.ui.home

import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Queue

data class HomeState(
    val queueList: List<Queue> = emptyList(),
    val fallback: Fallback?,
    val currentUser: String?,
)
