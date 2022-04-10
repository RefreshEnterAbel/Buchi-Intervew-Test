package com.refresh.enter.abel.buchi.activity.ui.adopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.refresh.enter.abel.buchi.databinding.RequestAdoptFragmentBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel

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

        return binding.root

    }


}