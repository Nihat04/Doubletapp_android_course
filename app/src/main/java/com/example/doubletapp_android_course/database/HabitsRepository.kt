package com.example.doubletapp_android_course.database

import androidx.lifecycle.LiveData
import com.example.doubletapp_android_course.model.dataClasses.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsRepository(private val habitsDb: HabitsDb) {
    private val ioScope = CoroutineScope(Dispatchers.IO)

    fun getAllHabits(): LiveData<List<Habit>> {
        return habitsDb.habitDao.getAllItems()
    }

    fun updateHabit(habit: Habit) {
        ioScope.launch {
            habitsDb.habitDao.updateItem(habit)
        }
    }

    fun addHabit(habit: Habit) {
        ioScope.launch {
            habitsDb.habitDao.insertItem(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        ioScope.launch {
            habitsDb.habitDao.deleteItem(habit)
        }
    }
}