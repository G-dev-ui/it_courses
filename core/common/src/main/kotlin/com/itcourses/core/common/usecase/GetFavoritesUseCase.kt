package com.itcourses.core.common.usecase

import com.itcourses.core.common.repo.CoursesRepository

class GetFavoritesUseCase(
    private val repository: CoursesRepository,
) {
    operator fun invoke() = repository.getFavorites()
}

