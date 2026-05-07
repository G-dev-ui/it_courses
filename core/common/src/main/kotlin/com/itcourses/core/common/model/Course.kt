package com.itcourses.core.common.model

data class Course(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val publishDate: String,
    val isFavorite: Boolean,
)

