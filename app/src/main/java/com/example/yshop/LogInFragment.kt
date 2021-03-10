package com.example.yshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yshop.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {

    lateinit var binding    : FragmentLogInBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater)
        return binding.root
    }
}