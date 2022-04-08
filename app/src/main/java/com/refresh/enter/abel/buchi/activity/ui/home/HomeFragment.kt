package com.refresh.enter.abel.buchi.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.refresh.enter.abel.buchi.adapter.HomeRecyclerViewAdapter
import com.refresh.enter.abel.buchi.databinding.FragmentHomeBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private var mViewModel: HomeActivityViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView
                (inflater: LayoutInflater,
                 container: ViewGroup?,
                 savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity())[HomeActivityViewModel::class.java]
        setAdapterView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapterView() {
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.homeRecyclerView.layoutManager = manager
        mViewModel?.getHomeSection()?.observe(viewLifecycleOwner){
            val adapter = HomeRecyclerViewAdapter(this.requireContext(), it)
            binding.homeRecyclerView.adapter = adapter
        }

    }
}