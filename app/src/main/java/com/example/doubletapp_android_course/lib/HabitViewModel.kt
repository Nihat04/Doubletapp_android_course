package com.example.doubletapp_android_course.lib

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp_android_course.Habit

class HabitViewModel: ViewModel() {
    val habits = MutableLiveData<MutableList<Habit>>(mutableListOf())

    fun addHabit(habit: Habit) {
        val currentHabits = habits.value ?: mutableListOf()
        val index = currentHabits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            currentHabits[index] = habit
        } else {
            currentHabits.add(habit)
        }

        habits.value = currentHabits
    }

    fun setHabits(habitList: MutableList<Habit>) {
        habits.value = habitList
    }

    fun generateId(): Int {
        return System.currentTimeMillis().toInt()
    }
}