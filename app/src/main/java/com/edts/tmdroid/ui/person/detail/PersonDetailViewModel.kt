package com.edts.tmdroid.ui.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.ui.model.PersonDetail
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mediaRepository: MediaRepository,
) : ViewModel() {

    private val args = PersonDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val personId = args.person.id

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val personDetail = MutableLiveData<PersonDetail>()

    val state: LiveData<PersonDetailState> = combineTuple(isLoading, personDetail)
        .map { (isLoading, personDetail) ->
            PersonDetailState(
                isLoading = isLoading ?: false,
                personDetail = personDetail,
            )
        }

    private val errorEmitter: EventEmitter<String> = EventEmitter()
    val errorMessage: EventSource<String> = errorEmitter

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = mediaRepository.getPersonDetail(personId)) {
                is Ok -> result.value.let(personDetail::setValue)
                is Err -> errorEmitter.emit(result.error)
            }

            isLoading.value = false
        }
    }
}
