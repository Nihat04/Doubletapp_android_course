package com.example.doubletapp_android_course.database

import android.app.Application
import kotlin.getValue

class App : Application() {
    val database by lazy { HabitsDb.createDataBase(this) }
}