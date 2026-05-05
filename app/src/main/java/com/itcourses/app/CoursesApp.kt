package com.itcourses.app

import android.app.Application
import com.itcourses.app.di.appModule
import com.itcourses.feature.course.di.courseModule
import com.itcourses.feature.favorites.di.favoritesModule
import com.itcourses.feature.home.di.homeModule
import com.itcourses.feature.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoursesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoursesApp)
            modules(
                appModule,
                homeModule,
                favoritesModule,
                profileModule,
                courseModule,
            )
        }
    }
}

