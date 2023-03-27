package com.edts.tmdroid.data.remote

import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): GetMoviesResponse
}
