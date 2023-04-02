package com.edts.tmdroid.data

import com.edts.tmdroid.data.common.MediaType
import com.edts.tmdroid.data.local.SessionManager
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.QueueEntity
import com.edts.tmdroid.data.local.entity.ReviewDao
import com.edts.tmdroid.data.local.entity.ReviewEntity
import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Media
import com.edts.tmdroid.ui.model.Person
import com.edts.tmdroid.ui.model.PersonDetail
import com.edts.tmdroid.ui.model.Queue
import com.edts.tmdroid.ui.model.Review
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class MediaRepository @Inject constructor(
    private val tmdbService: TmdbService,
    private val queueDao: QueueDao,
    private val reviewDao: ReviewDao,
    private val sessionManager: SessionManager,
) {

    private suspend fun getMovies(category: String, page: Int): Result<List<Media.Movie>, String> {
        return try {
            val response = tmdbService.getMovies(category, page)
            val movies = response.results.map(Media::from)
            Ok(movies)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getTopRatedMovies(page: Int) = getMovies("top_rated", page)
    suspend fun getUpcomingMovies(page: Int) = getMovies("upcoming", page)
    suspend fun getNowPlayingMovies(page: Int) = getMovies("now_playing", page)
    suspend fun getPopularMovies(page: Int) = getMovies("popular", page)

    suspend fun searchMovies(query: String, page: Int): Result<List<Media.Movie>, String> {
        return try {
            val response = tmdbService.searchMovies(query, page)
            val movies = response.results.map(Media::from)
            Ok(movies)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    private suspend fun getTvShows(category: String, page: Int): Result<List<Media.Tv>, String> {
        return try {
            val response = tmdbService.getTvShows(category, page)
            val tvShows = response.results.map(Media::from)
            Ok(tvShows)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getPopularTvShows(page: Int) = getTvShows("popular", page)
    suspend fun getTopRatedTvShows(page: Int) = getTvShows("top_rated", page)
    suspend fun getOnTheAirTvShows(page: Int) = getTvShows("on_the_air", page)
    suspend fun getAiringTodayTvShows(page: Int) = getTvShows("airing_today", page)

    suspend fun searchTvShows(query: String, page: Int): Result<List<Media.Tv>, String> {
        return try {
            val response = tmdbService.searchTvShows(query, page)
            val tvShows = response.results.map(Media::from)
            Ok(tvShows)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getMediaDetail(id: Int, type: MediaType): Result<Media, String> {
        return try {
            val media: Media = when (type) {
                MediaType.Movie -> tmdbService.getMovie(id).let(Media::from)
                MediaType.Tv -> tmdbService.getTv(id).let(Media::from)
            }

            Ok(media)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getPopularPeople(page: Int): Result<List<Person>, String> {
        return try {
            val response = tmdbService.getPopularPeople(page)
            val people = response.results.map(Person::from)

            Ok(people)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun searchPeople(query: String, page: Int): Result<List<Person>, String> {
        return try {
            val response = tmdbService.searchPeople(query, page)
            val tvShows = response.results.map(Person::from)
            Ok(tvShows)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getPersonDetail(id: Int): Result<PersonDetail, String> {
        return try {
            val response = tmdbService.getPerson(id)
            val people = response.let(PersonDetail::from)

            Ok(people)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun addToQueue(media: Media, type: MediaType) {
        val user = sessionManager.current.first() ?: return

        val entity = QueueEntity(
            media_id = media.id,
            user = user,
            title = when (media) {
                is Media.Movie -> media.title
                is Media.Tv -> media.name
            },
            poster_url = media.posterUrl,
            media_type = type,
        )

        queueDao.save(entity)
    }

    suspend fun removeFromQueue(id: Int, type: MediaType) {
        val user = sessionManager.current.first() ?: return
        queueDao.deleteMedia(id, type, user)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun isSaved(id: Int, type: MediaType): Flow<Boolean> {
        return sessionManager.current
            .filterNotNull()
            .flatMapLatest { user ->
                queueDao.isMediaSaved(id, type, user)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWatchList(): Flow<List<Queue>> {
        return sessionManager.current
            .filterNotNull()
            .flatMapLatest { user ->
                queueDao
                    .getLatest(user)
                    .map(Queue::from)
            }
    }

    suspend fun upsertReview(entity: ReviewEntity) {
        reviewDao.upsert(entity)
    }

    suspend fun deleteReview(entity: ReviewEntity) {
        reviewDao.delete(entity)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getReviews(id: Int, type: MediaType): Flow<List<Review>> {
        return sessionManager.current
            .filterNotNull()
            .flatMapLatest { user ->
                reviewDao
                    .getByMedia(id, type)
                    .map { list ->
                        list.map {
                            Review.from(it, it.name == user)
                        }
                    }
            }
    }

    private fun Exception.toErr(): Err<String> {
        val message = localizedMessage
            ?: message
            ?: "Unknown error"

        return Err(message)
    }
}
