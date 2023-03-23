package com.edts.tmdroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.edts.tmdroid.data.local.entity.FavoriteMovieDao
import com.edts.tmdroid.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [
        FavoriteMovieEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            val instance = buildDatabase(context)
            INSTANCE = instance
            instance
        }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "tmdroid.db"
        ).build()
    }
}
