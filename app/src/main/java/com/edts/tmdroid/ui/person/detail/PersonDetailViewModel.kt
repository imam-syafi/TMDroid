package com.edts.tmdroid.ui.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.PersonDetail
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tmdbService: TmdbService,
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

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            // Call API service
            val response = tmdbService.getPerson(personId)

            // Happy path
            personDetail.value = response.let(PersonDetail::from)

            isLoading.value = false
        }
    }
}
