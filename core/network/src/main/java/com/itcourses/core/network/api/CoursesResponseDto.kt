package com.itcourses.core.network.api

data class CoursesResponseDto(
    val courses: List<CourseDto>,
)

data class CourseDto(
    val id: Long,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
)

