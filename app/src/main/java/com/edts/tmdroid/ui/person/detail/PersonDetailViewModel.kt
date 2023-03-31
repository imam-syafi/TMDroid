package com.edts.tmdroid.ui.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.PersonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tmdbService: TmdbService,
) : ViewModel() {

    private val args = PersonDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _state = MutableLiveData(PersonDetailState())
    val state: LiveData<PersonDetailState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = tmdbService.getPerson(args.person.id)
            val detail = response.let(PersonDetail::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                personDetail = detail,
            )
        }
    }
}
