package com.example.doubletapp_android_course.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DiffUtil
import com.example.doubletapp_android_course.HabitAdapter
import com.example.doubletapp_android_course.R
import com.example.doubletapp_android_course.databinding.FragmentHabitsListBinding
import com.example.doubletapp_android_course.lib.HabitViewModel
import com.example.doubletapp_android_course.lib.HabitDiffCallback
import com.example.doubletapp_android_course.model.enums.HabitType
import kotlin.getValue

class HabitsListFragment : Fragment() {
    private lateinit var binding: FragmentHabitsListBinding
    private val viewModel: HabitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val habitType = arguments?.getSerializable(ARG_HABIT_TYPE) as? HabitType ?: HabitType.POSITIVE
        val adapter = HabitAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.habits.observe(viewLifecycleOwner) { habits ->
            val filteredHabits = habits
                .filter { it.type == habitType }
                .sortedByDescending { it.priority }

            val diffCallback = HabitDiffCallback(adapter.getHabits(), filteredHabits)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            adapter.updateHabits(filteredHabits)
            diffResult.dispatchUpdatesTo(adapter)
        }

        adapter.setOnItemClickListener { habit ->
            val fragment = CreateHabitFragment().apply {
                arguments = bundleOf("habit" to habit)
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        private const val ARG_HABIT_TYPE = "habit_type"

        fun newInstance(habitType: HabitType): HabitsListFragment {
            return HabitsListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_HABIT_TYPE, habitType)
                }
            }
        }
    }
}
