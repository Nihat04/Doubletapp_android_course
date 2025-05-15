package com.example.doubletapp_android_course.database

import androidx.lifecycle.LiveData
import com.example.doubletapp_android_course.model.dataClasses.Habit

class HabitsRepository(private val habitsDb: HabitsDb) {
    fun getHabitById(id: String): Habit? {
        return habitsDb.habitDao.getItemById(id)
    }

    fun getAllHabits(): LiveData<List<Habit>> {
        return habitsDb.habitDao.getAllItems()
    }

    fun updateHabit(habit: Habit) {
        habitsDb.habitDao.updateItem(habit)
    }

    fun addHabit(habit: Habit) {
        habitsDb.habitDao.insertItem(habit)
    }

    fun deleteHabit(habit: Habit) {
        habitsDb.habitDao.deleteItem(habit)
    }
}