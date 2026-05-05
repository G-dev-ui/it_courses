package com.itcourses.core.common.usecase

import com.itcourses.core.common.AppResult
import com.itcourses.core.common.model.Course
import com.itcourses.core.common.repo.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UseCasesTest {
    @Test
    fun `getCourses returns repository flow`() = runTest {
        val repo = FakeRepo()
        val useCase = GetCoursesUseCase(repo)

        repo.courses.value = listOf(
            Course(1, "t", "d", "10", "5.0", "2024-01-01", "2024-01-01", false),
        )

        val result = useCase().firstValue()
        assertEquals(1, (result as AppResult.Success).value.size)
    }

    @Test
    fun `toggleFavorite flips value`() = runTest {
        val repo = FakeRepo()
        repo.courses.value = listOf(
            Course(1, "t", "d", "10", "5.0", "2024-01-01", "2024-01-01", false),
        )
        val toggle = ToggleFavoriteUseCase(repo)
        toggle(1).firstValue()

        val course = GetCourseUseCase(repo)(1).firstValue() as AppResult.Success
        assertEquals(true, course.value.isFavorite)
    }

    private class FakeRepo : CoursesRepository {
        val courses = MutableStateFlow<List<Course>>(emptyList())

        override fun getCourses(): Flow<AppResult<List<Course>>> =
            courses.map { AppResult.Success(it) }

        override fun toggleFavorite(courseId: Long): Flow<AppResult<Unit>> {
            courses.value = courses.value.map { c ->
                if (c.id == courseId) c.copy(isFavorite = !c.isFavorite) else c
            }
            return MutableStateFlow(AppResult.Success(Unit))
        }

        override fun getCourse(courseId: Long): Flow<AppResult<Course>> =
            courses.map { list -> AppResult.Success(list.first { it.id == courseId }) }

        override fun getFavorites(): Flow<AppResult<List<Course>>> =
            courses.map { list -> AppResult.Success(list.filter { it.isFavorite }) }
    }
}

private suspend fun <T> Flow<T>.firstValue(): T = first()

