package com.edts.tmdroid.data

import com.edts.tmdroid.data.remote.TmdbService
import com.edts.tmdroid.ui.model.Media
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class MediaRepository(
    private val tmdbService: TmdbService,
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

    private fun Exception.toErr(): Err<String> {
        val message = localizedMessage
            ?: message
            ?: "Unknown error"

        return Err(message)
    }
}
