package com.example.doubletapp_android_course.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import com.example.doubletapp_android_course.Habit
import com.example.doubletapp_android_course.R
import com.example.doubletapp_android_course.databinding.FragmentCreateHabitBinding
import com.example.doubletapp_android_course.lib.HabitViewModel
import com.google.android.material.snackbar.Snackbar

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private var habitId: Int? = null
    private val viewModel: HabitViewModel by activityViewModels()

    companion object {
        private const val ARG_HABIT_ID = "habit_id"

        fun newInstance(habitId: Int? = null): CreateHabitFragment {
            val fragment = CreateHabitFragment()
            val args = Bundle()
            habitId?.let { args.putInt(ARG_HABIT_ID, it) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addArrays()
        applyEditState()
        addColorPicker()


        bindCreateButton()
    }

    private fun addArrays() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPrioritySpinner.adapter = adapter
        }
    }

    private fun applyEditState() {
        habitId = arguments?.getInt(ARG_HABIT_ID, -1)?.takeIf { it != -1 }
        val habit = viewModel.habits.value?.find { it.id == habitId }

        if (habit != null) {
            binding.habitNameInput.setText(habit.name)
            binding.habitDescriptionInput.setText(habit.description)
            binding.count.setText(habit.count.toString())
            binding.frequency.setText(habit.frequency.toString())

            if (habit.color != null) {
                binding.colorContainer.setBackgroundColor(habit.color)
            }

            val priorityArray = resources.getStringArray(R.array.priority_array)
            val priorityIndex = priorityArray.indexOf(habit.priority)
            if (priorityIndex >= 0) {
                binding.habitPrioritySpinner.setSelection(priorityIndex)
            }


            if (habit.type != null) {
                setRadio(binding.typeRadio, habit.type)
            }
        }
    }

    private fun addColorPicker() {
        val squareSize = 200
        val marginSize = (squareSize * 0.25).toInt()

        for (i in 0 until 16) {
            val color = Color.HSVToColor(floatArrayOf(i * (360f / 16), 1f, 1f))
            val square = View(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(squareSize, squareSize).apply {
                    setMargins(marginSize, marginSize, marginSize, marginSize)
                }

                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    setColor(color)
                    setStroke(5, Color.BLACK)
                    cornerRadius = 20f
                }

                background = drawable

                setOnClickListener {
                    Snackbar.make(it, "Selected color: #${Integer.toHexString(color)}", Snackbar.LENGTH_SHORT).show()
                    binding.colorContainer.setBackgroundColor(color)
                }
            }
            binding.colorContainer.addView(square)
        }
    }

    private fun bindCreateButton() {
        binding.habitSaveButton.setOnClickListener {
            val name = binding.habitNameInput.text.toString().trim()
            val description = binding.habitDescriptionInput.text.toString().trim()
            val priority = binding.habitPrioritySpinner.selectedItem.toString()
            val type = getRadio(binding.typeRadio)
            val countText = binding.count.text.toString().trim()
            val frequencyText = binding.frequency.text.toString().trim()
            var color: Int? = null

            if(binding.colorContainer.background != null) {
                color = getColor(binding.colorContainer.background)
            }

            var isValid = true

            if (name.isEmpty()) {
                binding.habitNameInput.error = "Введите название"
                isValid = false
            }

            if (description.isEmpty()) {
                binding.habitDescriptionInput.error = "Введите описание"
                isValid = false
            }

            if (countText.isEmpty()) {
                binding.count.error = "Введите количество"
                isValid = false
            }

            if (frequencyText.isEmpty()) {
                binding.frequency.error = "Введите частоту"
                isValid = false
            }

            if (!isValid) return@setOnClickListener

            val newId = habitId ?: viewModel.generateId()

            val habit = Habit(
                newId,
                name,
                description,
                priority,
                type,
                countText.toInt(),
                frequencyText.toInt(),
                color
            )

            viewModel.addHabit(habit)

            parentFragmentManager.popBackStack()
        }
    }

    private fun getRadio(radioGroup: RadioGroup): String? {
        val selectedRadioId = radioGroup.checkedRadioButtonId

        if (selectedRadioId != -1) {
            val selectedRadioButton = binding.root.findViewById<RadioButton>(selectedRadioId)
            val selectedText = selectedRadioButton.text.toString()

            return selectedText
        }

        return null
    }

    private fun setRadio(radioGroup: RadioGroup, type: String) {
        when (type) {
            "Работа" -> radioGroup.check(R.id.radio_work)
            "Тренировка" -> radioGroup.check(R.id.radio_exercise)
        }
    }

    private fun getColor(container: Drawable): Int? {
        try {
            if (container is ColorDrawable) {
                return container.color
            }

            return null
        } catch (e: NullPointerException) {
            return null
        }

    }
}