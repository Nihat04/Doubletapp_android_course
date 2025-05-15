package com.example.doubletapp_android_course

import android.app.Application
import com.example.doubletapp_android_course.database.HabitsDb
import kotlin.getValue

class App : Application() {
    val database by lazy { HabitsDb.createDataBase(this) }
}