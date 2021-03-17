package com.example.yshop.registerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentRegisterBinding

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

        // hide the action bar
        //(activity as AppCompatActivity).supportActionBar?.hide()


        // go login page
        binding.tvLoginId.setOnClickListener {
            // Launch the register screen when the user clicks on te text Register
            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
        }

        // set icon button press back to login page in toolBar
        binding.toolbarRegisterFragmentId.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarRegisterFragmentId.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_registerFragment_to_logInFragment)
        }

        // btn register
        binding.btnRegisterId.setOnClickListener {
           registerViewModel.registerUser(requireActivity() , view , binding.cbTermsAndConditionId)
        }
    }

}