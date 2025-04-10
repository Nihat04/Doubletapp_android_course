package com.example.doubletapp_android_course.model.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doubletapp_android_course.database.HabitsRepository

class HabitListViewModelFactory(
    private val repository: HabitsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}