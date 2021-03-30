package com.example.yshop.forgetpasswordfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        // hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()


        // set icon button press back to login page in toolBar
        binding.toolbarForgetPasswordFragmentId.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarForgetPasswordFragmentId.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_logInFragment)
        }

        binding.btnSubmitId.setOnClickListener {
            forgetPasswordViewModel.forgetPassword(requireActivity() , view)
        }


    }

}