package com.edts.tmdroid.ui.person.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.Fallback
import com.edts.tmdroid.ui.model.Person
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val fallback = MutableLiveData<Fallback?>()
    private val people = MutableLiveData<List<Person>>()

    val state: LiveData<PersonListState> = combineTuple(isLoading, fallback, people)
        .map { (isLoading, fallback, people) ->
            PersonListState(
                isLoading = isLoading ?: false,
                fallback = fallback,
                people = people ?: emptyList(),
            )
        }

    private val errorEmitter: EventEmitter<String> = EventEmitter()
    val errorMessage: EventSource<String> = errorEmitter

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = mediaRepository.getPopularPeople()) {
                is Ok -> {
                    result.value.let {
                        people.value = it
                        fallback.value = if (it.isEmpty()) Fallback.NO_RESULT else null
                    }
                }
                is Err -> {
                    fallback.value = Fallback.FETCH_ERROR
                    errorEmitter.emit(result.error)
                }
            }

            isLoading.value = false
        }
    }

    fun onRefresh() {
        people.value = emptyList()
        fetchData()
    }
}
