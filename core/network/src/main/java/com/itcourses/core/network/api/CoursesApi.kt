package com.itcourses.core.network.api

import retrofit2.http.GET

interface CoursesApi {
    @GET("/courses")
    suspend fun getCourses(): CoursesResponseDto
}

