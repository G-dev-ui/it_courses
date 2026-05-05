package com.itcourses.feature.profile.presentation

import com.itcourses.feature.profile.presentation.model.MyCourseUiModel

data class ProfileUiState(
    val isLoading: Boolean = false,
    val myCourses: List<MyCourseUiModel> = emptyList(),
    val errorMessage: String? = null,
)

