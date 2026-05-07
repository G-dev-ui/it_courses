package com.itcourses.feature.profile.presentation.model

data class MyCourseUiModel(
    val id: Long,
    val title: String,
    val progressPercent: Int,
    val lessonsDone: Int,
    val lessonsTotal: Int,
    val isFavorite: Boolean,
)

