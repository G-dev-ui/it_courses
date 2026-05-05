package com.itcourses.feature.home.presentation.model

data class CourseUiModel(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val isFavorite: Boolean,
    val rate: String,
    val startDateIso: String,
    val publishDateIso: String,
)

