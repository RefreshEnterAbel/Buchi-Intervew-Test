package com.refresh.enter.abel.buchi.activity.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.refresh.enter.abel.buchi.adapter.SearchResultRecyclerViewAdapter
import com.refresh.enter.abel.buchi.databinding.FragmentSearchResultBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private var mViewModel: HomeActivityViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
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
        binding.searchResultRecyclerView.layoutManager = manager
        mViewModel?.getPets()?.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                val adapter = SearchResultRecyclerViewAdapter(this.requireContext(), it)
                binding.searchResultRecyclerView.adapter = adapter
            }
        }
    }


}