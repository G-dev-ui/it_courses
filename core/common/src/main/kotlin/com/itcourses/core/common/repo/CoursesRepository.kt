package com.itcourses.core.common.repo

import com.itcourses.core.common.AppResult
import com.itcourses.core.common.model.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    fun getCourses(): Flow<AppResult<List<Course>>>
    fun toggleFavorite(courseId: Long): Flow<AppResult<Unit>>
    fun getCourse(courseId: Long): Flow<AppResult<Course>>
    fun getFavorites(): Flow<AppResult<List<Course>>>
}

