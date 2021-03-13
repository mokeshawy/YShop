package com.example.yshop.loginfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    lateinit var binding    : FragmentLogInBinding
    val logInViewModel      : LogInViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // connect whit view model
        binding.lifecycleOwner      = this
        binding.logInVarViewModel   = logInViewModel

        // ProgressBar INVISIBLE
        binding.proBarLoginId.visibility = View.INVISIBLE

        // go register page
        binding.tvRegisterId.setOnClickListener {
            // launch the login screen when the user clicks on the text Login
            findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }



    }

}