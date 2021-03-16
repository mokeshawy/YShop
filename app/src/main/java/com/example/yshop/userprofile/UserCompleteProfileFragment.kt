package com.example.yshop.userprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.yshop.databinding.FragmentUserCompleteProfileBinding
import com.example.yshop.userprofile.UserCompleteProfileViewMode

class UserCompleteProfileFragment : Fragment() {
    lateinit var binding            : FragmentUserCompleteProfileBinding
    val userCompleteProfileViewMode : UserCompleteProfileViewMode by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserCompleteProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit userCompleteProfileVarMode
        binding.lifecycleOwner = this
        binding.userCompleteProfileVarMode = userCompleteProfileViewMode

        // hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
}