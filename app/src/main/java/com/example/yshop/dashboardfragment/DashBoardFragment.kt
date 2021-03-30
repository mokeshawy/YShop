package com.example.yshop.dashboardfragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentDashBoardBinding

class DashBoardFragment : Fragment() {

    lateinit var binding    : FragmentDashBoardBinding
    val dashBoardViewModel  : DashBoardFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)

        // Connect whit viewModel
        binding.lifecycleOwner = this
        binding.dashBoardVarModel = dashBoardViewModel
    }

    // Create the icon menu in the action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dashboard_menu,menu)
    }

    // SelectItem for menu we want create the page for it
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_settings ->{
                findNavController().navigate(R.id.action_dashBoardFragment_to_settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}