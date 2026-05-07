package com.itcourses.core.common.usecase

import com.itcourses.core.common.repo.CoursesRepository

class GetCoursesUseCase(
    private val repository: CoursesRepository,
) {
    operator fun invoke() = repository.getCourses()
}

