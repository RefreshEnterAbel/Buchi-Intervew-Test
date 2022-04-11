package com.refresh.enter.abel.buchi.activity.ui.adopt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.adapter.AdoptSlideImageRecyclerViewAdapter
import com.refresh.enter.abel.buchi.databinding.FragmentAdoptBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel

class AdoptFragment : Fragment() {

    private var _binding: FragmentAdoptBinding? = null
    private var mViewModel: HomeActivityViewModel? = null
    private val args: AdoptFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdoptBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity())[HomeActivityViewModel::class.java]
        intiView()

        binding.adoptMeBtn.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(AdoptFragmentDirections.actionAdoptFragmentToRequestAdoptFragment())
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun intiView() {
        val manager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.imageRecyclerView.layoutManager = manager
        val pet = mViewModel?.getPet(args.petIndex)
        val adapter = AdoptSlideImageRecyclerViewAdapter(this.requireContext(), pet?.photos)
        binding.imageRecyclerView.adapter = adapter
        binding.typeTextView.text = pet?.type
        binding.ageTextView.text = resources.getString(R.string.age_text, pet?.age)
        binding.genderTextView.text = resources.getString(R.string.gender_text, pet?.gender)
        binding.sizeTextView.text = resources.getString(R.string.size_text, pet?.size)
        if (pet?.good_with_children == true) {
            binding.notGoodWithChildTextView.text = resources.getString(R.string.good_with_children)
        } else {
            binding.notGoodWithChildTextView.text =
                resources.getString(R.string.not_good_with_children)
        }
    }
}