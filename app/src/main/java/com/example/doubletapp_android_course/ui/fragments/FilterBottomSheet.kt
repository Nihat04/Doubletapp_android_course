package com.example.doubletapp_android_course.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.doubletapp_android_course.database.App
import com.example.doubletapp_android_course.database.HabitsRepositoryProvider
import com.example.doubletapp_android_course.databinding.FragmentHabitsBinding
import com.example.doubletapp_android_course.model.views.HabitListViewModel
import com.example.doubletapp_android_course.model.views.HabitListViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHabitsBinding
    private lateinit var habitListViewModel: HabitListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = (requireActivity().application as App).database
        val repository = HabitsRepositoryProvider.getRepository(database)
        val factory = HabitListViewModelFactory(repository)
        habitListViewModel = ViewModelProvider(requireActivity(), factory)[HabitListViewModel::class.java]

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Optional: No implementation needed if not using
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                habitListViewModel.filterHabitsByName(query)
            }

            override fun afterTextChanged(p0: Editable?) {
                // Optional: No implementation needed if not using
            }
        })

    }


    companion object {
        const val TAG = "ModalBottomSheet"
    }
}