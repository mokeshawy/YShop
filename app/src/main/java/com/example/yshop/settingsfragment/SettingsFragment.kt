package com.example.yshop.settingsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.databinding.FragmentSettingsBinding

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

    }
}