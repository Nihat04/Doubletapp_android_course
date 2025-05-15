package com.example.doubletapp_android_course.api

import retrofit2.Retrofit

object ApiFactory {
    private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .build()

    val service: BackendService = retrofit.create(BackendService::class.java)
}