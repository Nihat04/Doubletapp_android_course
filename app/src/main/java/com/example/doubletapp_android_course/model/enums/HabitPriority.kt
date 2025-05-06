package com.example.doubletapp_android_course.model.enums

import com.example.doubletapp_android_course.R

enum class HabitPriority(val impactName: Int) {
    HIGH(R.string.priority_high),
    MIDDLE(R.string.priority_middle),
    LOW((R.string.priority_low));
}