package com.example.doubletapp_android_course.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doubletapp_android_course.model.dataClasses.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE id = :id")
    fun getItemById(id: String): Habit?

    @Query("SELECT * " + "FROM habits")
    fun getAllItems(): LiveData<List<Habit>>

    @Update
    fun updateItem(nameEntity: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(nameEntity: Habit)

    @Delete
    fun deleteItem(nameEntity: Habit)
}