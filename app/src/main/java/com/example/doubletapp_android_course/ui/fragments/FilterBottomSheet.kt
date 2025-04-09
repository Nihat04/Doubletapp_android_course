package com.example.doubletapp_android_course.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doubletapp_android_course.databinding.FragmentFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilterBottomSheetBinding
//    private val habitListViewModel: HabitListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val activity = requireActivity()
//
//        binding.searchField.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                // Optional: No implementation needed if not using
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val query = s?.toString()?.trim() ?: ""
//                habitListViewModel.filterHabitsByName(query)
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                // Optional: No implementation needed if not using
//            }
//        })

    }


    companion object {
        const val TAG = "ModalBottomSheet"
    }
}