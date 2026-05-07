package com.itcourses.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT courseId FROM favorites")
    fun observeFavoriteIds(): Flow<List<Long>>

    @Query("SELECT courseId FROM favorites")
    suspend fun getFavoriteIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<FavoriteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE courseId = :courseId")
    suspend fun delete(courseId: Long)
}

