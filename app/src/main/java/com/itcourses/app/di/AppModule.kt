package com.itcourses.app.di

import com.itcourses.core.network.api.CoursesApi
import com.itcourses.core.networkmock.MockNetworkFactory
import com.itcourses.core.common.repo.CoursesRepository
import com.itcourses.core.network.repo.CoursesRepositoryImpl
import com.itcourses.core.common.usecase.GetCourseUseCase
import com.itcourses.core.common.usecase.GetCoursesUseCase
import com.itcourses.core.common.usecase.GetFavoritesUseCase
import com.itcourses.core.common.usecase.ToggleFavoriteUseCase
import com.itcourses.core.network.storage.FavoritesStore
import okhttp3.OkHttpClient
import org.koin.dsl.module

val appModule = module {
    single<OkHttpClient> { MockNetworkFactory.createOkHttpClient(get()) }
    single<CoursesApi> { MockNetworkFactory.createCoursesApi(get()) }
    single { FavoritesStore(get()) }
    single<CoursesRepository> { CoursesRepositoryImpl(get(), get()) }

    factory { GetCoursesUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { GetCourseUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }
}

