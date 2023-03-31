package com.edts.tmdroid.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.QueueEntity
import com.edts.tmdroid.data.local.entity.ReviewDao
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.model.Review
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tmdbService: TmdbService,
    private val queueDao: QueueDao,
    reviewDao: ReviewDao,
) : ViewModel() {

    private val args = MovieDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val movieId = args.movieId

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val movie = MutableLiveData<Movie>()

    private val isSaved: LiveData<Boolean> = queueDao
        .isMediaSaved(movieId, MediaType.Movie)
        .asLiveData()

    private val reviews: LiveData<List<Review>> = reviewDao
        .getByMedia(mediaId = movieId, mediaType = MediaType.Movie)
        .map(Review::from)
        .asLiveData()

    val state: LiveData<MovieDetailState> = combineTuple(isLoading, isSaved, movie, reviews)
        .map { (isLoading, isSaved, movie, reviews) ->
            MovieDetailState(
                isLoading = isLoading ?: false,
                isSaved = isSaved ?: false,
                movie = movie,
                reviews = reviews ?: emptyList(),
            )
        }

    init {
        fetchData()
    }

    fun onToggle() {
        viewModelScope.launch {
            movie.value?.let { movie ->
                if (isSaved.value == true) {
                    queueDao.deleteMedia(args.movieId, MediaType.Movie)
                } else {
                    val entity = QueueEntity(
                        media_id = args.movieId,
                        title = movie.title,
                        poster_url = movie.posterUrl,
                        media_type = MediaType.Movie,
                    )

                    queueDao.save(entity)
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.value = true

            // Call API service
            val response = tmdbService.getMovie(args.movieId)

            // Happy path
            movie.value = response.let(Movie::from)

            isLoading.value = false
        }
    }
}
