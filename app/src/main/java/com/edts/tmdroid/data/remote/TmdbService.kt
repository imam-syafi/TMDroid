package com.edts.tmdroid.data.remote

import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import com.edts.tmdroid.data.remote.response.GetPeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

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

    @GET("person/popular")
    suspend fun getPopularPeople(): GetPeopleResponse
}
