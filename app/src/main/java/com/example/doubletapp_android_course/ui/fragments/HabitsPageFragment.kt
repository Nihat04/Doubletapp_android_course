package com.example.doubletapp_android_course.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.doubletapp_android_course.R
import com.example.doubletapp_android_course.databinding.FragmentHabitsBinding
import com.example.doubletapp_android_course.model.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HabitsPageFragment : Fragment()  {
    private lateinit var binding: FragmentHabitsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHabitsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.negative_habit)
                1 -> getString(R.string.positive_habit)
                else -> throw IllegalArgumentException("не удалось найти $position")
            }
        }.attach()

        binding.createButton.setOnClickListener {
            val fragment = CreateHabitFragment()
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.filtersButton.setOnClickListener {
            val modalBottomSheet = FilterBottomSheet()
            modalBottomSheet.show(activity.supportFragmentManager, FilterBottomSheet.TAG)
        }

    }
}