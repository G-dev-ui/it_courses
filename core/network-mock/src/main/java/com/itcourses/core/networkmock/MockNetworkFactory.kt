package com.itcourses.core.networkmock

import android.content.Context
import com.itcourses.core.network.NetworkFactory
import com.itcourses.core.network.api.CoursesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object MockNetworkFactory {
    const val BASE_URL = "https://mock.local/"

    fun createOkHttpClient(context: Context): OkHttpClient {
        val routes = mapOf(
            "/courses" to "mock/api.json",
        )

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder()
            .addInterceptor(AssetJsonInterceptor(context, routes))
            .addInterceptor(logging)
            .build()
    }

    fun createCoursesApi(okHttpClient: OkHttpClient): CoursesApi {
        val moshi = NetworkFactory.createMoshi()
        val retrofit = NetworkFactory.createRetrofit(
            baseUrl = BASE_URL,
            okHttpClient = okHttpClient,
            moshi = moshi,
        )
        return retrofit.create(CoursesApi::class.java)
    }
}

