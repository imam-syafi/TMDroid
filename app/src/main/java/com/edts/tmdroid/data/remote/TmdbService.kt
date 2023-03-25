package com.edts.tmdroid.data.remote

import com.edts.tmdroid.BuildConfig
import com.edts.tmdroid.data.remote.response.GetMoviesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
    ): GetMoviesResponse

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        private val auth = Interceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .addQueryParameter("language", "en-US")
                .build()

            val request = original.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(auth)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

        val instance: TmdbService = retrofit.create(TmdbService::class.java)
    }
}
