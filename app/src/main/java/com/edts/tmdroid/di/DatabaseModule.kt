package com.edts.tmdroid.di

import android.content.Context
import com.edts.tmdroid.data.local.AppDatabase
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.ReviewDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    fun provideReviewDao(database: AppDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    fun provideQueueDao(database: AppDatabase): QueueDao {
        return database.queueDao()
    }
}
