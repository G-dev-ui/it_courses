package com.itcourses.feature.favorites.di

import com.itcourses.feature.favorites.presentation.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel(get(), get()) }
}

