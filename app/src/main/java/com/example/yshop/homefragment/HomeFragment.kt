package com.example.yshop.homefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding    : FragmentHomeBinding
    val homeViewModel       : HomeFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Connect whit viewModel
        binding.lifecycleOwner  = this
        binding.homeVarModel    = homeViewModel
    }
}