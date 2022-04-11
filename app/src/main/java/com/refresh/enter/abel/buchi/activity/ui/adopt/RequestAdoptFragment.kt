package com.refresh.enter.abel.buchi.activity.ui.adopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.refresh.enter.abel.buchi.databinding.RequestAdoptFragmentBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel
import android.text.TextUtils
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.model.AddCustomer
import android.graphics.drawable.ColorDrawable

import android.app.AlertDialog
import android.graphics.Color
import android.widget.Button

import android.widget.TextView
import com.refresh.enter.abel.buchi.model.DialogData


class RequestAdoptFragment : Fragment() {

    private var _binding: RequestAdoptFragmentBinding? = null
    private var mViewModel: HomeActivityViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RequestAdoptFragmentBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        mViewModel?.mDialogData?.observe(viewLifecycleOwner,{
            massageDialog(it)
        })
        binding.btnSend.setOnClickListener {
            if (formValidation()) {
                val addCustomer = AddCustomer()
                addCustomer.name = binding.nameInput.editText?.text.toString()
                addCustomer.phone = binding.phoneInput.editText?.text.toString()
                mViewModel?.addCustomer(addCustomer)
            }
        }
        return binding.root
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

    private fun formValidation(): Boolean {
        var validate = true
        if (TextUtils.isEmpty(binding.nameInput.editText?.text.toString())) {
            binding.nameInput.isErrorEnabled = true
            binding.nameInput.error =
                getString(R.string.error_input_isEmpty, getString(R.string.name))
            validate = false
        } else {
            binding.nameInput.isErrorEnabled = false
            binding.nameInput.error = null
        }
        if (TextUtils.isEmpty(binding.phoneInput.editText?.text.toString())) {
            binding.phoneInput.isErrorEnabled = true
            binding.phoneInput.error = getString(
                R.string.error_input_isEmpty, getString(R.string.mobile_number)
            )
            validate = false
        } else {
            binding.phoneInput.isErrorEnabled = false
            binding.phoneInput.error = null
        }
        return validate
    }

}