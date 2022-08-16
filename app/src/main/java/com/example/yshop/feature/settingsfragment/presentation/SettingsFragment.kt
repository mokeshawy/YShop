package com.example.yshop.feature.settingsfragment.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentSettingsBinding
import com.example.yshop.feature.settingsfragment.domain.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    lateinit var binding    : FragmentSettingsBinding
    val settingsViewModel   : SettingsViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Connect whit viewModel
        binding.lifecycleOwner      = this
        binding.settingsVarModel    = settingsViewModel


        // set icon button press back to login page in toolBar
        binding.toolbarSettingsFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarSettingsFragment.setNavigationOnClickListener { view ->
            findNavController().navigate(R.id.action_settingsFragment_to_dashBoardFragment)
        }


        // Show details for user logIn
        settingsViewModel.showData(requireActivity() , binding.tvName , binding.tvGender , binding.tvEmail , binding.tvMobileNumber , binding.ivUserPhotoId)

        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_userCompleteProfileFragment)
        }

        // user log out
        binding.btnLogout.setOnClickListener {
            settingsViewModel.signOut(requireActivity() , view)
        }

        binding.llAddress.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_addressListFragment)
        }

    }
}