package com.itcourses.feature.home.presentation

import com.itcourses.feature.home.presentation.model.CourseUiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val items: List<CourseUiModel> = emptyList(),
    val errorMessage: String? = null,
    val query: String = "",
    val sortAscending: Boolean = false,
)

