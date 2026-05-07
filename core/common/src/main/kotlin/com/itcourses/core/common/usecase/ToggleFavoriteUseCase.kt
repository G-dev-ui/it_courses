package com.itcourses.core.common.usecase

import com.itcourses.core.common.repo.CoursesRepository

class ToggleFavoriteUseCase(
    private val repository: CoursesRepository,
) {
    operator fun invoke(courseId: Long) = repository.toggleFavorite(courseId)
}

