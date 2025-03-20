package com.example.doubletapp_android_course.lib

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.doubletapp_android_course.ui.fragments.WorkFragment
import com.example.doubletapp_android_course.ui.fragments.WorkoutFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WorkoutFragment()
            1 -> WorkFragment()
            else -> throw IllegalArgumentException("Не удалось найти $position")
        }
    }
}