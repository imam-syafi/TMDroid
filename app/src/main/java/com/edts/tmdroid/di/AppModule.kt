package com.edts.tmdroid.di

import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.data.remote.TmdbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideMediaRepository(
        tmdbService: TmdbService,
    ): MediaRepository {
        return MediaRepository(tmdbService)
    }
}
