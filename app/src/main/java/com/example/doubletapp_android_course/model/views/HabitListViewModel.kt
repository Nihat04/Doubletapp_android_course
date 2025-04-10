package com.example.doubletapp_android_course.model.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doubletapp_android_course.model.dataClasses.Habit

class HabitListViewModel: ViewModel() {
    private val mutableHabits: MutableLiveData<MutableList<Habit>> = MutableLiveData(mutableListOf())
    private val mutableVisibleHabits: MutableLiveData<MutableList<Habit>> = MutableLiveData(mutableListOf())

    val habits: LiveData<MutableList<Habit>> = mutableVisibleHabits

    fun addHabit(habit: Habit) {
        val currentHabits = mutableHabits.value ?: mutableListOf()

        val index = currentHabits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            currentHabits[index] = habit
        } else {
            currentHabits.add(habit)
        }

        mutableHabits.value = currentHabits
        mutableVisibleHabits.value = currentHabits
    }

    fun setHabits(habitList: MutableList<Habit>) {
        mutableHabits.value = habitList
        mutableVisibleHabits.value = habitList
    }

    fun filterHabitsByName(query: String) {
        val allHabits = mutableHabits.value ?: mutableListOf()
        if (query.isBlank()) {
            mutableVisibleHabits.value = allHabits
        } else {
            mutableVisibleHabits.value = allHabits
                .filter { it.name.contains(query, ignoreCase = true) }
                .toMutableList()
        }
    }
}