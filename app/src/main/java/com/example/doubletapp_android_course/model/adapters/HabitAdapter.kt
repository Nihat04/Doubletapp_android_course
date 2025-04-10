package com.example.doubletapp_android_course.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp_android_course.model.dataClasses.Habit
import com.example.doubletapp_android_course.model.viewHolders.HabitViewHolder
import com.example.doubletapp_android_course.R

class HabitAdapter(private val habits: MutableList<Habit> ) : RecyclerView.Adapter<HabitViewHolder>() {

    private var clickEvent: ((Habit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.item_habit, parent, false))
    }

    override fun getItemCount(): Int = habits.size

    fun getHabits(): MutableList<Habit> = habits

    fun setOnItemClickListener(listener: (Habit) -> Unit) {
        this.clickEvent = listener
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        holder.bind(habit)

        if(habit.color != null) {
            holder.itemView.setBackgroundColor(habit.color)
        }

        holder.itemView.setOnClickListener {
            clickEvent?.invoke(habit)
        }
    }

    fun updateHabit(updatedHabit: Habit) {
        val index = habits.indexOfFirst { it.id == updatedHabit.id }
        if (index != -1) {
            habits[index] = updatedHabit
            notifyItemChanged(index)
        } else {
            habits.add(updatedHabit)
            notifyItemInserted(habits.size - 1)
        }
    }

    fun updateHabits(updatedHabits: List<Habit>) {
        if (updatedHabits.isNotEmpty()) {
            habits.clear()
            habits.addAll(updatedHabits)
            notifyDataSetChanged()
        }
    }

    fun containsHabit(id: String): Boolean {
        return habits.any { it.id == id }
    }

    fun addHabit(habit: Habit) {
        if (!habits.any { it.id == habit.id }) {
            habits.add(habit)
            notifyItemInserted(habits.size - 1)
        }
    }
}