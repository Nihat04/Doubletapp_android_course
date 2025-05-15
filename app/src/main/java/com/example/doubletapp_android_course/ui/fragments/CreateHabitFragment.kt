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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.doubletapp_android_course.model.dataClasses.Habit
import com.example.doubletapp_android_course.R
import com.example.doubletapp_android_course.databinding.FragmentCreateHabitBinding
import com.example.doubletapp_android_course.model.enums.HabitPriority
import com.example.doubletapp_android_course.model.views.HabitEditViewModel
import com.example.doubletapp_android_course.model.views.HabitListViewModel
import com.example.doubletapp_android_course.model.enums.HabitType
import com.google.android.material.snackbar.Snackbar

class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private var habit: Habit? = null
    private lateinit var viewModel: HabitListViewModel
    private val editViewModel: HabitEditViewModel by activityViewModels()

    companion object {
        fun newInstance(habit: Habit): CreateHabitFragment {
            return CreateHabitFragment().apply {
                arguments = bundleOf("habit" to habit)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)

        habit = arguments?.getParcelable<Habit>("habit")

        editViewModel.saveHabit(habit)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HabitListViewModel::class.java]

        addArrays()
        provideHabit()
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

    private fun provideHabit() {
        val habit = this.habit

        if (habit != null) {
            binding.habitNameInput.setText(habit.name)
            binding.habitDescriptionInput.setText(habit.description)
            binding.count.setText(habit.count.toString())
            binding.frequency.setText(habit.frequency.toString())

            if (habit.color != null) {
                binding.colorContainer.setBackgroundColor(habit.color)
            }

            val priorityIndex = habit.priority.ordinal
            if (priorityIndex >= 0) {
                binding.habitPrioritySpinner.setSelection(priorityIndex)
            }


            setRadio(binding.typeRadio, habit.type)
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
        binding.habitSaveButton.setOnClickListener { it ->
            val name = binding.habitNameInput.text.toString().trim()
            val description = binding.habitDescriptionInput.text.toString().trim()
            val type = getRadio(binding.typeRadio)

            val selectedItem = binding.habitPrioritySpinner.selectedItem
            var priority = HabitPriority.entries.find {
                getString(it.displayName) == selectedItem?.toString()
            }

            if(priority == null) {
                priority = HabitPriority.LOW
            }

            if (type == null) {
                Snackbar.make(it, "Пожалуйста выберите привычку", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val countText = binding.count.text.toString().trim()
            val frequencyText = binding.frequency.text.toString().trim()
            var color: Int? = null

            if (binding.colorContainer.background != null) {
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

            val savedHabit = editViewModel.habit.value
            val newHabit = Habit(
                savedHabit?.uid,
                name,
                description,
                priority,
                type,
                countText.toInt(),
                frequencyText.toInt(),
                color
            )
            editViewModel.habit.value?.let { viewModel.addHabit(it) }

            parentFragmentManager.popBackStack()
        }
    }

    private fun getRadio(radioGroup: RadioGroup): HabitType? {
        val selectedRadioId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = radioGroup.findViewById<RadioButton>(selectedRadioId)

        return when (selectedRadioButton?.id) {
            R.id.radio_positive -> HabitType.POSITIVE
            R.id.radio_negative -> HabitType.NEGATIVE
            else -> null

        }
    }

    private fun setRadio(radioGroup: RadioGroup, type: HabitType) {
        when (type) {
            HabitType.POSITIVE -> radioGroup.check(R.id.radio_positive)
            HabitType.NEGATIVE -> radioGroup.check(R.id.radio_negative)
        }
    }

    private fun getColor(container: Drawable): Int? {
        try {
            if (container is ColorDrawable) {
                return container.color
            }

            return null
        } catch (_: NullPointerException) {
            return null
        }

    }
}