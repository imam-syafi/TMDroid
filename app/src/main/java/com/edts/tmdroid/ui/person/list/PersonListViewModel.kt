package com.edts.tmdroid.ui.person.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Person
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val tmdbService: TmdbService,
) : ViewModel() {

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val people = MutableLiveData<List<Person>>()

    val state: LiveData<PersonListState> = combineTuple(isLoading, people)
        .map { (isLoading, people) ->
            PersonListState(
                isLoading = isLoading ?: false,
                people = people ?: emptyList(),
            )
        }

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            // Call API service
            val response = tmdbService.getPopularPeople()

            // Happy path
            people.value = response.results.map(Person::from)

            isLoading.value = false
        }
    }

    fun onRefresh() {
        people.value = emptyList()
        fetchData()
    }
}
