package com.edts.tmdroid.data

import com.edts.tmdroid.data.common.MediaType
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MediaRepository(
    private val tmdbService: TmdbService,
    private val queueDao: QueueDao,
    private val reviewDao: ReviewDao,
) {

    private suspend fun getMovies(category: String): Result<List<Media.Movie>, String> {
        return try {
            val response = tmdbService.getMovies(category)
            val movies = response.results.map(Media::from)
            Ok(movies)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getTopRatedMovies() = getMovies("top_rated")
    suspend fun getUpcomingMovies() = getMovies("upcoming")
    suspend fun getNowPlayingMovies() = getMovies("now_playing")
    suspend fun getPopularMovies() = getMovies("popular")

    private suspend fun getTvShows(category: String): Result<List<Media.Tv>, String> {
        return try {
            val response = tmdbService.getTvShows(category)
            val tvShows = response.results.map(Media::from)
            Ok(tvShows)
        } catch (e: Exception) {
            e.toErr()
        }
    }

    suspend fun getPopularTvShows() = getTvShows("popular")
    suspend fun getTopRatedTvShows() = getTvShows("top_rated")
    suspend fun getOnTheAirTvShows() = getTvShows("on_the_air")
    suspend fun getAiringTodayTvShows() = getTvShows("airing_today")

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

    suspend fun getPopularPeople(): Result<List<Person>, String> {
        return try {
            val response = tmdbService.getPopularPeople()
            val people = response.results.map(Person::from)

            Ok(people)
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
        val entity = QueueEntity(
            media_id = media.id,
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
        queueDao.deleteMedia(id, type)
    }

    fun isSaved(id: Int, type: MediaType): Flow<Boolean> = queueDao.isMediaSaved(id, type)

    fun getWatchList(): Flow<List<Queue>> = queueDao
        .getLatest()
        .map(Queue::from)

    suspend fun upsertReview(entity: ReviewEntity) {
        reviewDao.upsert(entity)
    }

    suspend fun deleteReview(entity: ReviewEntity) {
        reviewDao.delete(entity)
    }

    fun getReviews(id: Int, type: MediaType): Flow<List<Review>> = reviewDao
        .getByMedia(id, type)
        .map(Review::from)

    private fun Exception.toErr(): Err<String> {
        val message = localizedMessage
            ?: message
            ?: "Unknown error"

        return Err(message)
    }
}
