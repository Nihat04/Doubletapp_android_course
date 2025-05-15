package com.example.doubletapp_android_course.model.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapp_android_course.database.HabitsRepository
import com.example.doubletapp_android_course.model.dataClasses.Habit
import kotlinx.coroutines.launch

class HabitListViewModel(private val repository: HabitsRepository): ViewModel() {

    val habits: LiveData<List<Habit>> = repository.getAllHabits()

    private val mutableVisibleHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    val visibleHabits: LiveData<List<Habit>> = mutableVisibleHabits

    init {
        habits.observeForever { habitList ->
            mutableVisibleHabits.value = habitList
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun filterHabitsByName(query: String) {
        val currentHabits = habits.value ?: return
        mutableVisibleHabits.value = if (query.isBlank()) {
            currentHabits
        } else {
            currentHabits.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}