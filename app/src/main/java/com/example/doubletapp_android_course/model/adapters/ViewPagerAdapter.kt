package com.example.doubletapp_android_course.model.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doubletapp_android_course.model.enums.HabitType
import com.example.doubletapp_android_course.ui.fragments.HabitsListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitsListFragment.Companion.newInstance(HabitType.NEGATIVE)
            1 -> HabitsListFragment.Companion.newInstance(HabitType.POSITIVE)
            else -> throw IllegalArgumentException("Не удалось найти $position")
        }
    }
}