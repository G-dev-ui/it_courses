package com.itcourses.feature.favorites.presentation

import com.itcourses.feature.favorites.presentation.model.CourseUiModel

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val items: List<CourseUiModel> = emptyList(),
    val errorMessage: String? = null,
)

