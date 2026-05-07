package com.itcourses.feature.course.presentation

data class CourseUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val description: String = "",
    val rate: String = "",
    val startDateIso: String = "",
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
)

