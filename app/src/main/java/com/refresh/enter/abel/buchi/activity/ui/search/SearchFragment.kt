package com.refresh.enter.abel.buchi.activity.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.databinding.FragmentSearchBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private var mViewModel: HomeActivityViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity())[HomeActivityViewModel::class.java]
        binding.look.setOnClickListener {
            searchPetFromForm()
        }
        setAutoCompleteListView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAutoCompleteListView() {
        // dropdown menu
        binding.goodWithChildrenAutoComplete.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.text_list_item,
                resources.getStringArray(R.array.good_with_children_array)
            )
        )
        // dialog choose
        binding.typeAutoComplete.setOnClickListener {
            downDropListSelection(
                binding.typeAutoComplete,
                resources.getStringArray(R.array.type_array),
                resources.getString(R.string.types)
            )
        }
        // dialog choose
        binding.ageAutoComplete.setOnClickListener {
            downDropListSelection(
                binding.ageAutoComplete,
                resources.getStringArray(R.array.age_array),
                resources.getString(R.string.age)
            )
        }
        // dialog choose
        binding.genderAutoComplete.setOnClickListener {
            downDropListSelection(
                binding.genderAutoComplete,
                resources.getStringArray(R.array.gender_array),
                resources.getString(R.string.gender)
            )
        }
        // dialog choose
        binding.sizeAutoComplete.setOnClickListener {
            downDropListSelection(
                binding.sizeAutoComplete,
                resources.getStringArray(R.array.size_array),
                resources.getString(R.string.size)
            )
        }
    }

    private fun searchPetFromForm() {

        mViewModel?.searchPet(
            50,
            binding.typeInput.editText?.text.toString(),
            binding.genderInput.editText?.text.toString(),
            binding.sizeInput.editText?.text.toString(),
            binding.sizeInput.editText?.text.toString(),
            binding.goodWithChildrenInput.editText?.text.toString() == "Yes"
        )
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_navigation_search_to_searchResultFragment)
    }

    private fun downDropListSelection(
        view: AutoCompleteTextView,
        stringArray: Array<String>,
        title: String
    ) {
        val checkedItems = BooleanArray(stringArray.size)
        val selectArray: ArrayList<Int> = ArrayList()
        val formSelect: List<String> = view.text.toString().split(",")
        for (i in stringArray.indices) {
            if (formSelect.contains(stringArray[i])) {
                checkedItems[i] = true
                selectArray.add(i)
            } else {
                selectArray.remove(Integer.valueOf(i))
            }
        }
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        mBuilder.setTitle(title)
        mBuilder.setMultiChoiceItems(
            stringArray, checkedItems
        ) { _, position, isChecked ->
            if (isChecked) {
                selectArray.add(position)
            } else {
                selectArray.remove(Integer.valueOf(position))
            }
        }
        mBuilder.setCancelable(false)
        mBuilder.setPositiveButton(getString(R.string.okay)) { dialogInterface, _ ->
            var item = ""
            for (l in selectArray.indices) {
                item = String.format("%s%s", item, stringArray[selectArray[l]])
                if (l != selectArray.size - 1) {
                    item = "$item,"
                }
            }
            view.setText(item)
            dialogInterface.dismiss()
        }
        mBuilder.setNegativeButton(getString(R.string.dismiss)) { dialogInterface, _ -> dialogInterface.dismiss() }
        val mDialog: AlertDialog = mBuilder.create()
        mDialog.show()
    }
}