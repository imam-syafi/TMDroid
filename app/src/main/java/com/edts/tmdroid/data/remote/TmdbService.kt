package com.edts.tmdroid.data.remote

import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import com.edts.tmdroid.data.remote.response.GetPeopleResponse
import com.edts.tmdroid.data.remote.response.GetTvShowsResponse
import com.edts.tmdroid.data.remote.response.MovieDto
import com.edts.tmdroid.data.remote.response.PersonDetailDto
import com.edts.tmdroid.data.remote.response.TvDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): MovieDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): GetMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): GetMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): GetMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): GetMoviesResponse

    @GET("tv/{id}")
    suspend fun getTv(@Path("id") id: Int): TvDto

    @GET("tv/popular")
    suspend fun getPopularTvShows(): GetTvShowsResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(): GetTvShowsResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(): GetTvShowsResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShows(): GetTvShowsResponse

    @GET("person/popular")
    suspend fun getPopularPeople(): GetPeopleResponse

    @GET("person/{id}")
    suspend fun getPerson(@Path("id") id: Int): PersonDetailDto
}
