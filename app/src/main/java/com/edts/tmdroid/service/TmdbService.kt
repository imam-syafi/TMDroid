package com.edts.tmdroid.service

import com.edts.tmdroid.BuildConfig
import com.edts.tmdroid.service.response.GetMoviesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = LANGUAGE,
    ): GetMoviesResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val LANGUAGE = "en-US"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

        val instance: TmdbService = retrofit.create(TmdbService::class.java)
    }
}