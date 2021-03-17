package com.example.yshop.dashboardfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.databinding.FragmentDashBoardBinding

class DashBoardFragment : Fragment() {

    lateinit var binding    : FragmentDashBoardBinding
    val dashBoardViewModel  : DashBoardFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit viewModel
        binding.lifecycleOwner = this
        binding.dashBoardVarModel = dashBoardViewModel
    }
}