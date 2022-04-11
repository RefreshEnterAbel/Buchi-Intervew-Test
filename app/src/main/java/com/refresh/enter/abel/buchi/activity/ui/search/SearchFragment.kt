package com.refresh.enter.abel.buchi.activity.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.databinding.FragmentSearchBinding
import com.refresh.enter.abel.buchi.model.DialogData
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private var mViewModel: HomeActivityViewModel? = null
    private var dialog: Dialog? = null

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
        mViewModel?.mDialogData?.observe(viewLifecycleOwner, {
            massageDialog(it)
        })
        mViewModel?.mWait?.observe(viewLifecycleOwner, {
            showWait(it)
        })

        mViewModel?.mSearch?.observe(viewLifecycleOwner, {
            if (it) {
                Navigation.findNavController(binding.root)
                    .navigate(
                        SearchFragmentDirections
                            .actionNavigationSearchToSearchResultFragment()
                    )
                mViewModel?.mSearch!!.value = false
                mViewModel?.mWait!!.value = false
            }
        })
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

    private fun showWait(wait: Boolean) {
        // set loading dialog
        if (wait) {
            dialog = Dialog(requireContext())
            dialog?.setContentView(R.layout.loading_dialog)
            dialog?.setCancelable(false)
            if (dialog?.window != null) {
                dialog!!.window?.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog?.show()
        } else {
            dialog?.dismiss()
            dialog = null
        }
    }

    private fun massageDialog(dialog: DialogData?) {
        if (dialog != null) {
            val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
            val layoutView: View
            val positiveButton: Button
            val message: TextView
            val title: TextView
            val messageDialog: AlertDialog
            when (dialog.type) {
                1 -> {
                    // display success dialog
                    layoutView =
                        layoutInflater.inflate(R.layout.dialog_success_message, binding.root, false)
                    dialogBuilder.setView(layoutView)
                    positiveButton = layoutView.findViewById(R.id.btn_position)
                    message = layoutView.findViewById(R.id.message)
                    message.text = dialog.message
                    messageDialog = dialogBuilder.create()
                    messageDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    messageDialog.show()
                    positiveButton.setOnClickListener {
                        mViewModel!!.mWait.value = false
                        messageDialog.dismiss()
                    }
                }
                4 -> {
                    layoutView =
                        layoutInflater.inflate(R.layout.dialog_network_error, binding.root, false)
                    dialogBuilder.setView(layoutView)
                    title = layoutView.findViewById(R.id.title)
                    positiveButton = layoutView.findViewById(R.id.btn_position)
                    if (dialog.title != null) {
                        title.text = dialog.title
                    }
                    message = layoutView.findViewById(R.id.message)
                    message.text = dialog.message
                    messageDialog = dialogBuilder.create()
                    messageDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    messageDialog.setCancelable(false)
                    messageDialog.show()
                    positiveButton.setOnClickListener {
                        mViewModel!!.mWait.value = false
                        messageDialog.dismiss()
                    }
                }
            }
            mViewModel?.mDialogData?.value = null
        }
    }
}