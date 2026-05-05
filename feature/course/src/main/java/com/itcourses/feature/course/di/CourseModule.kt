package com.itcourses.feature.course.di

import com.itcourses.feature.course.presentation.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val courseModule = module {
    viewModel { CourseViewModel(get(), get()) }
}

