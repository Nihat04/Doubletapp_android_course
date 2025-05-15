package com.example.doubletapp_android_course.lib

import androidx.recyclerview.widget.DiffUtil
import com.example.doubletapp_android_course.model.dataClasses.Habit

class HabitDiffCallback(
    private val oldList: List<Habit>,
    private val newList: List<Habit>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].uid == newList[newItemPosition].uid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
