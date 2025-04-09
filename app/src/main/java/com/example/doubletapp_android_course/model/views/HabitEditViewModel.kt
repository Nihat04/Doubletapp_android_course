package com.example.doubletapp_android_course.model.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp_android_course.model.dataClasses.Habit
import com.example.doubletapp_android_course.model.enums.HabitType

class HabitEditViewModel: ViewModel() {
    var currentHabit = MutableLiveData<Habit?>(null)
    val habit: LiveData<Habit?> get() = currentHabit

    fun saveHabit(name: String, description: String, priority: String, type: HabitType, count: Int, frequency: Int, color: Int?) {
        val newHabit = Habit(
            id = currentHabit.value?.id ?: generateId(),
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

    private fun generateId(): Int {
        return System.currentTimeMillis().toInt()
    }
}