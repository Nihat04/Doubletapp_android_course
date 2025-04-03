import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.doubletapp_android_course.databinding.FragmentFilterBottomSheetBinding
import com.example.doubletapp_android_course.lib.HabitViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBottomSheetBinding
    private val viewModel: HabitViewModel by activityViewModels()

    private val TAG: String = "bsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.applyButton.setOnClickListener {
            apply()
            dismiss()
        }

        binding.resetButton.setOnClickListener {
            viewModel.reset()
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sortRadioGroup.check(binding.sortTitle.id)

        fillUI()
    }

    private fun fillUI() {
        val filters = viewModel.getCurrentFilters()

        binding.frequencyEditText.setText(filters[FilterType.FREQUENCY] ?: "")
        binding.quantityEditText.setText(filters[FilterType.QUANTITY] ?: "")

        val sortingType = viewModel.getCurrentSortingType()

        for (i in 0 until binding.sortRadioGroup.childCount) {
            val radioButton = binding.sortRadioGroup.getChildAt(i) as RadioButton
            if (radioButton.text == sortingType.description) {
                binding.sortRadioGroup.check(radioButton.id)
                break
            }
        }

        binding.searchEditText.setText(viewModel.getCurrentSearchingWord())
    }

    private fun apply() {
        val filters: MutableMap<FilterType, String> = mutableMapOf()

        val frequency = binding.frequencyEditText.text.toString()
        val quantity = binding.quantityEditText.text.toString()

        val searchingWord = binding.searchEditText.text.toString()

        Log.i(TAG, "data: $frequency $quantity")
        if (frequency.isNotBlank()) {
            filters[FilterType.FREQUENCY] = frequency
        }

        if (quantity.isNotBlank()) {
            filters[FilterType.QUANTITY] = quantity
        }

        val selectedSortingType = getSelectedSortingType()

        viewModel.sortByField(selectedSortingType)
        viewModel.applyFilters(filters)
        if (searchingWord.isNotBlank()) {
            viewModel.findByWord(searchingWord)
        }
    }

    private fun getSelectedSortingType(): SortingType {
        return when (binding.sortRadioGroup.checkedRadioButtonId) {
            binding.sortQuantity.id -> SortingType.QUANTITY
            binding.sortFrequency.id -> SortingType.FREQUENCY
            else -> SortingType.DEFAULT
        }
    }
}