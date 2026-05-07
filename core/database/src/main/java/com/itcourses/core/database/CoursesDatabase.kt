package com.itcourses.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class CoursesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

