package com.example.doubletapp_android_course.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubletapp_android_course.HabitAdapter
import com.example.doubletapp_android_course.R
import com.example.doubletapp_android_course.databinding.FragmentWorkBinding
import com.example.doubletapp_android_course.lib.HabitViewModel
import kotlin.getValue

class WorkoutFragment : Fragment() {
    private lateinit var binding: FragmentWorkBinding
    private val viewModel: HabitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HabitAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner) { habits ->
            val workoutHabits = habits
                .filter { it.type == "Тренировка" }
                .sortedByDescending { it.priority }
            adapter.updateHabits(workoutHabits)
        }

        adapter.setOnItemClickListener { habit ->
            val fragment = CreateHabitFragment.newInstance(habit.id)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}