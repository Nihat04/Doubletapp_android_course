package com.example.doubletapp_android_course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HabitAdapter(private val habits: List<Habit>, private val listener: OnHabitClickListener ) : RecyclerView.Adapter<HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.item_habit, parent, false))
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        holder.bind(habit)

        holder.itemView.setOnClickListener {
            listener.onHabitClick(habit)
        }

        if(habit.color != null) {
            holder.itemView.setBackgroundColor(habit.color)
        }
    }
}