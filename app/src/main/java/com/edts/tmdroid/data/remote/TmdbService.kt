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

    @GET("movie/{category}")
    suspend fun getMovies(@Path("category") category: String): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): GetMoviesResponse

    @GET("tv/{id}")
    suspend fun getTv(@Path("id") id: Int): TvDto

    @GET("tv/{category}")
    suspend fun getTvShows(@Path("category") category: String): GetTvShowsResponse

    @GET("person/popular")
    suspend fun getPopularPeople(): GetPeopleResponse

    @GET("person/{id}")
    suspend fun getPerson(@Path("id") id: Int): PersonDetailDto
}
