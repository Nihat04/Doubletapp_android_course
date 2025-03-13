package com.example.doubletapp_android_course

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubletapp_android_course.databinding.MainActivityBinding

interface OnHabitClickListener {
    fun onHabitClick(habit: Habit)
}

class MainActivity : ComponentActivity(), OnHabitClickListener {
    private val data = mutableListOf<Habit>()
    private lateinit var customAdapter: HabitAdapter

    private val createHabitLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val newHabit = result.data?.getParcelableExtra<Habit>("habit")
            newHabit?.let {
                data.add(it)
                customAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapter = HabitAdapter(data, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = customAdapter

        binding.createButton.setOnClickListener {
            val intent = Intent(this, CreateHabitActivity::class.java)
            createHabitLauncher.launch(intent)
        }
    }

    override fun onHabitClick(habit: Habit) {
        val intent = Intent(this, CreateHabitActivity::class.java)

        habit.let { intent.putExtra("habit", it) }

        createHabitLauncher.launch(intent)
    }
}