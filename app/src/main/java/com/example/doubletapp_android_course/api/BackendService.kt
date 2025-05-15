package com.example.doubletapp_android_course.api

import com.example.doubletapp_android_course.model.dataClasses.Habit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface BackendService {
    @Headers(
        "Authorization:7d17eabd-59ba-4e20-a0c6-25d39497e33e"
    )
    @GET("habit")
    suspend fun listHabits(): Response<List<Habit>>

    @Headers(
        "Authorization:7d17eabd-59ba-4e20-a0c6-25d39497e33e",
        "Content-Type: application/json"
    )
    @PUT("habit")
    suspend fun addHabit(@Body habit: Habit): Response<String>
}