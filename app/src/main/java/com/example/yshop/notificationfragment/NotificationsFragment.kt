package com.example.yshop.notificationfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    lateinit var binding        : FragmentNotificationsBinding
    val notificationViewModel   : NotificationFragmentViewModel by viewModels()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit viewModel
        binding.lifecycleOwner          = this
        binding.notificationVarModel    = notificationViewModel
    }
}