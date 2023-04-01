package com.edts.tmdroid.di

import com.edts.tmdroid.data.MediaRepository
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.ReviewDao
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
        queueDao: QueueDao,
        reviewDao: ReviewDao,
    ): MediaRepository {
        return MediaRepository(tmdbService, queueDao, reviewDao)
    }
}