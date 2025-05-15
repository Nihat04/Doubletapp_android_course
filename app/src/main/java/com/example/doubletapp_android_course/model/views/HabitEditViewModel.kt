package com.example.doubletapp_android_course.model.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp_android_course.model.dataClasses.Habit
import com.example.doubletapp_android_course.model.enums.HabitPriority
import com.example.doubletapp_android_course.model.enums.HabitType
import java.util.UUID

class HabitEditViewModel: ViewModel() {
    var currentHabit = MutableLiveData<Habit?>(null)
    val habit: LiveData<Habit?> get() = currentHabit

    fun saveHabit(habit: Habit?) {
        currentHabit.value = habit
    }

    fun generateHabit(uid: String? = null, name: String, description: String, priority: HabitPriority, type: HabitType, count: Int, frequency: Int, color: Int?) {
        val newHabit = Habit(
            uid = uid ?: generateId(),
            name = name,
            description = description,
            priority = priority,
            type = type,
            count = count,
            frequency = frequency,
            color = color
        )
        currentHabit.value = newHabit
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}