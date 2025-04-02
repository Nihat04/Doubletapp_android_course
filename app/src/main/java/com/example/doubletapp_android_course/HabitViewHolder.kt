package com.example.doubletapp_android_course

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val habitName: TextView = itemView.findViewById(R.id.name)
    private val habitDescription: TextView = itemView.findViewById(R.id.description)
    private val habitPriority: TextView = itemView.findViewById(R.id.priority)
    private val habitType: TextView = itemView.findViewById(R.id.type)
    private val habitFrequency: TextView = itemView.findViewById(R.id.frequency)

    @SuppressLint("SetTextI18n")
    fun bind(habit: Habit) {
        habitName.text = habit.name
        habitDescription.text = habit.description
        habitPriority.text = habit.priority
        habitType.text = habit.type.toString()
        habitFrequency.text = "${habit.count} раз за ${habit.frequency} дней"
    }

}