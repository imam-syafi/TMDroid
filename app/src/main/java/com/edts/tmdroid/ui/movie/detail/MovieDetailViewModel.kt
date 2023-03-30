package com.edts.tmdroid.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.QueueEntity
import com.edts.tmdroid.data.local.entity.ReviewDao
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Movie
import com.edts.tmdroid.ui.model.Review
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val tmdbService: TmdbService,
    private val queueDao: QueueDao,
    reviewDao: ReviewDao,
) : ViewModel() {

    private val _state = MutableLiveData(MovieDetailState())
    val state: LiveData<MovieDetailState> = _state

    val isSaved = queueDao.isMediaSaved(movieId, MediaType.Movie)
    val reviews: LiveData<List<Review>> = reviewDao
        .getByMedia(mediaId = movieId, mediaType = MediaType.Movie)
        .map(Review::from)

    init {
        fetchData()
    }

    fun onToggle() {
        viewModelScope.launch {
            _state.value?.movie?.let { movie ->
                if (isSaved.value == true) {
                    queueDao.deleteMedia(movieId, MediaType.Movie)
                } else {
                    val entity = QueueEntity(
                        media_id = movieId,
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
            _state.value = _state.value?.copy(isLoading = true)

            // Call API service
            val response = tmdbService.getMovie(movieId)
            val movie = response.let(Movie::from)

            // Happy path
            _state.value = _state.value?.copy(
                isLoading = false,
                movie = movie,
            )
        }
    }
}
