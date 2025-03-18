package com.example.doubletapp_android_course

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import com.example.doubletapp_android_course.databinding.CreateHabitActivityBinding
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class CreateHabitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CreateHabitActivityBinding.inflate(layoutInflater)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPrioritySpinner.adapter = adapter
        }

        val habit: Habit? = intent.getParcelableExtra("habit")

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

        val squareSize = 200
        val marginSize = (squareSize * 0.25).toInt()

        for (i in 0 until 16) {
            val color = Color.HSVToColor(floatArrayOf(i * (360f / 16), 1f, 1f))
            val square = View(this).apply {
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

        setContentView(binding.root)

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

            val habit = Habit(
                name,
                description,
                priority,
                type,
                countText.toInt(),
                frequencyText.toInt(),
                color
            )

            val resultIntent = Intent().apply {
                putExtra("habit", habit)
            }
            setResult(RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun getRadio(radioGroup: RadioGroup): String? {
        val selectedRadioId = radioGroup.checkedRadioButtonId

        if (selectedRadioId != -1) {
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioId)
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