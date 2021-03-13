package com.example.yshop.registerfragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    lateinit var binding    : FragmentRegisterBinding
    val registerViewModel   : RegisterViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // connect whit view model
        binding.lifecycleOwner              = this
        binding.registerVarViewModel        = registerViewModel

        // ProgressBar INVISIBLE
        binding.proBarRegisterId.visibility = View.INVISIBLE

        // go login page
        binding.tvLoginId.setOnClickListener {
            // Launch the register screen when the user clicks on te text Register
            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
        }

        // set icon button press back to login page in toolBar
        binding.toolbarRegisterFragmentId.setNavigationIcon(R.drawable.ic_black_color_back_24)
        binding.toolbarRegisterFragmentId.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
        }

        // btn register
        binding.btnRegisterId.setOnClickListener {
           registerViewModel.validateRegisterDetails(requireActivity() , binding.constraintId , binding.cbTermsAndConditionId)
        }
    }

}