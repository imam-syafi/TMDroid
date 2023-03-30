package com.edts.tmdroid.ui.home

import com.edts.tmdroid.ui.model.Queue

data class HomeState(
    val queueList: List<Queue> = emptyList(),
)
