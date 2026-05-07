package com.itcourses.core.network.repo

import com.itcourses.core.common.AppResult
import com.itcourses.core.common.model.Course
import com.itcourses.core.common.repo.CoursesRepository
import com.itcourses.core.database.FavoriteEntity
import com.itcourses.core.database.FavoritesDao
import com.itcourses.core.network.api.CoursesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CoursesRepositoryImpl(
    private val api: CoursesApi,
    private val favoritesDao: FavoritesDao,
) : CoursesRepository {

    private val mutex = Mutex()
    private val cache = MutableStateFlow<List<Course>?>(null)

    override fun getCourses(): Flow<AppResult<List<Course>>> = flow {
        ensureLoaded()
        emitAll(
            cache.filterNotNull()
                .map { AppResult.Success(it) as AppResult<List<Course>> },
        )
    }.catch { t ->
        emit(AppResult.Error(t))
    }

    override fun getFavorites(): Flow<AppResult<List<Course>>> =
        getCourses().map { result ->
            when (result) {
                is AppResult.Success -> AppResult.Success(result.value.filter { it.isFavorite })
                is AppResult.Error -> result
            }
        }

    override fun getCourse(courseId: Long): Flow<AppResult<Course>> =
        getCourses().map { result ->
            when (result) {
                is AppResult.Success -> AppResult.Success(result.value.first { it.id == courseId })
                is AppResult.Error -> result
            }
        }

    override fun toggleFavorite(courseId: Long): Flow<AppResult<Unit>> = flow {
        ensureLoaded()
        var newValue: Boolean? = null
        mutex.withLock {
            val list = requireNotNull(cache.value)
            cache.value = list.map { c ->
                if (c.id == courseId) {
                    val toggled = !c.isFavorite
                    newValue = toggled
                    c.copy(isFavorite = toggled)
                } else {
                    c
                }
            }
        }
        when (newValue) {
            true -> favoritesDao.insert(FavoriteEntity(courseId))
            false -> favoritesDao.delete(courseId)
            null -> Unit
        }
        emit(AppResult.Success(Unit))
    }

    private suspend fun ensureLoaded() {
        if (cache.value != null) return
        mutex.withLock {
            if (cache.value != null) return
            val remote = api.getCourses().courses
            favoritesDao.insertAll(remote.filter { it.hasLike }.map { FavoriteEntity(it.id) })
            val favorites = favoritesDao.getFavoriteIds().toSet()
            cache.value = remote.map { dto ->
                Course(
                    id = dto.id,
                    title = dto.title,
                    description = dto.text,
                    price = dto.price,
                    rate = dto.rate,
                    startDate = dto.startDate,
                    publishDate = dto.publishDate,
                    isFavorite = favorites.contains(dto.id),
                )
            }
        }
    }

}

