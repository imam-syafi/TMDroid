package com.edts.tmdroid.data.local.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {

    @Query("SELECT * FROM t_favorite_movie")
    fun getAll(): LiveData<List<FavoriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT * FROM t_favorite_movie WHERE id = :movieId)")
    fun isSaved(movieId: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: FavoriteMovieEntity)

    @Delete
    suspend fun delete(movie: FavoriteMovieEntity)
}
