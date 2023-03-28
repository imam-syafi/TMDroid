package com.edts.tmdroid.ui.person.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Person
import kotlinx.coroutines.launch

class PersonListViewModel(
    private val tmdbService: TmdbService,
) : ViewModel() {

    private val _state = MutableLiveData(PersonListState())
    val state: LiveData<PersonListState> = _state

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = tmdbService.getPopularPeople()
            val people = response.results.map(Person::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                people = people,
            )
        }
    }
}