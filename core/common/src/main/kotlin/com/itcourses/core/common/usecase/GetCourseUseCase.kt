package com.itcourses.core.common.usecase

import com.itcourses.core.common.repo.CoursesRepository

class GetCourseUseCase(
    private val repository: CoursesRepository,
) {
    operator fun invoke(courseId: Long) = repository.getCourse(courseId)
}

