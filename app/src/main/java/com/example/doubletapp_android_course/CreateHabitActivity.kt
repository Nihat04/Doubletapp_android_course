package com.example.doubletapp_android_course

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import com.example.doubletapp_android_course.databinding.CreateHabitActivityBinding

class CreateHabitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = CreateHabitActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.habitPrioritySpinner.adapter = adapter
        }

        binding.habitSaveButton.setOnClickListener {
            val habit = Habit(
                binding.habitNameInput.text.toString(),
                binding.habitDescriptionInput.text.toString(),
                binding.habitPrioritySpinner.selectedItem.toString(),
                getRadio(binding.typeRadio),
                binding.count.text.toString().toIntOrNull(),
                binding.frequency.text.toString().toIntOrNull(),
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
}