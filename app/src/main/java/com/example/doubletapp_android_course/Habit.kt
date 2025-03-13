package com.example.doubletapp_android_course

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val name: String,
    val description: String,
    val priority: String,
    val type: String?,
    val count: Int,
    val frequency: Int,
) : Parcelable