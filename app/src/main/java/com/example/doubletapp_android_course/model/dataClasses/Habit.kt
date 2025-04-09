package com.example.doubletapp_android_course.model.dataClasses

import android.os.Parcelable
import com.example.doubletapp_android_course.model.enums.HabitType
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val id: Int,
    val name: String,
    val description: String,
    val priority: String,
    val type: HabitType,
    val count: Int,
    val frequency: Int,
    val color: Int?
) : Parcelable