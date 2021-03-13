package com.example.yshop.forgetpasswordfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    lateinit var binding        : FragmentForgetPasswordBinding
    val forgetPasswordViewModel : ForgetPasswordViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect whit viewModel
        binding.lifecycleOwner          = this
        binding.forgetPasswordVarModel  = forgetPasswordViewModel
    }
}