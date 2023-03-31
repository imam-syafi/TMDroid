package com.edts.tmdroid.ui.tv.detail

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
import com.edts.tmdroid.ui.model.Review
import com.edts.tmdroid.ui.model.Tv
import com.zhuinden.livedatacombinetuplekt.combineTuple
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class TvDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val tmdbService: TmdbService,
    private val queueDao: QueueDao,
    reviewDao: ReviewDao,
) : ViewModel() {

    private val args = TvDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val tvId = args.tvId

    // Backing properties
    private val isLoading = MutableLiveData<Boolean>()
    private val tv = MutableLiveData<Tv>()

    private val isSaved: LiveData<Boolean> = queueDao
        .isMediaSaved(tvId, MediaType.Tv)
        .asLiveData()

    private val reviews: LiveData<List<Review>> = reviewDao
        .getByMedia(mediaId = tvId, mediaType = MediaType.Tv)
        .map(Review::from)
        .asLiveData()

    val state: LiveData<TvDetailState> = combineTuple(isLoading, isSaved, tv, reviews)
        .map { (isLoading, isSaved, tv, reviews) ->
            TvDetailState(
                isLoading = isLoading ?: false,
                isSaved = isSaved ?: false,
                tv = tv,
                reviews = reviews ?: emptyList(),
            )
        }

    init {
        fetchData()
    }

    fun onToggle() {
        viewModelScope.launch {
            tv.value?.let { tv ->
                if (isSaved.value == true) {
                    queueDao.deleteMedia(tvId, MediaType.Tv)
                } else {
                    val entity = QueueEntity(
                        media_id = tvId,
                        title = tv.name,
                        poster_url = tv.posterUrl,
                        media_type = MediaType.Tv,
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
            val response = tmdbService.getTv(tvId)

            // Happy path
            tv.value = response.let(Tv::from)

            isLoading.value = false
        }
    }
}
