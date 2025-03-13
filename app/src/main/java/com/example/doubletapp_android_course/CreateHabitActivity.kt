package com.example.doubletapp_android_course

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import com.example.doubletapp_android_course.databinding.CreateHabitActivityBinding

@Suppress("DEPRECATION")
class CreateHabitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CreateHabitActivityBinding.inflate(layoutInflater)

        val habit: Habit? = intent.getParcelableExtra("habit")

        if (habit != null) {
            binding.habitNameInput.setText(habit.name)
            binding.habitDescriptionInput.setText(habit.description)

            // Set the spinner selection based on habit's priority
            val priorityIndex = resources.getStringArray(R.array.priority_array).indexOf(habit.priority)
            if (priorityIndex >= 0) {
                binding.habitPrioritySpinner.setSelection(priorityIndex)
            }

            binding.count.setText(habit.count.toString())
            binding.frequency.setText(habit.frequency.toString())

            // Set the radio button based on habit type
            if (habit.type != null) {
                setRadio(binding.typeRadio, habit.type)
            }
        }

        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.habitPrioritySpinner.adapter = adapter
        }

        binding.habitSaveButton.setOnClickListener {

            val habit = Habit(
                binding.habitNameInput.text.toString(),
                binding.habitDescriptionInput.text.toString(),
                binding.habitPrioritySpinner.selectedItem.toString(),
                getRadio(binding.typeRadio),
                binding.count.text.toString().toInt(),
                binding.frequency.text.toString().toInt(),
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
            "Type1" -> radioGroup.check(R.id.radio_work) // Replace with your actual RadioButton IDs
            "Type2" -> radioGroup.check(R.id.radio_exercise)
            // Add more cases for other types as needed
        }
    }

}