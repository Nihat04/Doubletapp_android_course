package com.example.doubletapp_android_course.model.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doubletapp_android_course.api.ApiFactory.service
import com.example.doubletapp_android_course.model.dataClasses.Habit
import kotlinx.coroutines.launch

class HabitListViewModel : ViewModel() {

    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits

    private val visibleHabits = MutableLiveData<List<Habit>>()

    init {
        loadHabitsFromServer()
    }

    private fun loadHabitsFromServer() {
        viewModelScope.launch {
            try {
                val response = service.listHabits()
                if (response.isSuccessful) {
                    val habitList = response.body().orEmpty()
                    _habits.value = habitList
                    visibleHabits.value = habitList
                } else {
                    Log.e("ViewModel", "Server error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Network error: ${e.message}")
            }
        }
    }

    fun filterHabitsByName(query: String) {
        val all = _habits.value.orEmpty()
        visibleHabits.value = if (query.isBlank()) {
            all
        } else {
            all.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                val response = service.addHabit(habit)
                if (response.isSuccessful) {
                    Log.d("ViewModel", "Habit added successfully: ${response.body()}")
                    loadHabitsFromServer()
                } else {
                    Log.e("ViewModel", "Failed to add habit: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error adding habit: ${e.message}")
            }
        }
    }
}