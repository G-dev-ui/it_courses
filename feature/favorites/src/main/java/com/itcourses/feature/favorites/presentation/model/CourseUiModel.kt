package com.itcourses.feature.favorites.presentation.model

data class CourseUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val rate: String,
    val startDateIso: String,
)

