package com.edts.tmdroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edts.tmdroid.data.local.entity.FavoriteMovieDao
import com.edts.tmdroid.data.local.entity.FavoriteMovieEntity
import com.edts.tmdroid.data.local.entity.QueueDao
import com.edts.tmdroid.data.local.entity.QueueEntity
import com.edts.tmdroid.data.local.entity.ReviewDao
import com.edts.tmdroid.data.local.entity.ReviewEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
        ReviewEntity::class,
        QueueEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    abstract fun reviewDao(): ReviewDao

    abstract fun queueDao(): QueueDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val databaseBuilder = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "tmdroid.db",
            )

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
