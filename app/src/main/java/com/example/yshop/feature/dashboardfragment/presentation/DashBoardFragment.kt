package com.example.yshop.feature.dashboardfragment.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.core.utils.OptionBuilder
import com.example.yshop.databinding.FragmentDashBoardBinding
import com.example.yshop.feature.adapter.RecyclerDashBoardAdapter
import com.example.yshop.feature.dashboardfragment.data.model.request.ProductModel
import com.example.yshop.feature.dashboardfragment.domain.viewmodel.DashBoardFragmentViewModel

class DashBoardFragment : Fragment(), RecyclerDashBoardAdapter.OnClickProduct {

    lateinit var binding: FragmentDashBoardBinding
    private val dashBoardViewModel: DashBoardFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        // Show progress dialog
        OptionBuilder.showProgressDialog(
            resources.getString(R.string.please_wait),
            requireActivity()
        )
        // Call function get details all product in firebase to view dashboard
        dashBoardViewModel.getProductDetails(
            binding.rvDashboardItems,
            binding.tvNoDashboardItemsFound
        )
        dashBoardViewModel.getProductDetails.observe(viewLifecycleOwner, Observer {
            binding.rvDashboardItems.adapter = RecyclerDashBoardAdapter(it, this)
            OptionBuilder.hideProgressDialog()
        })
    }

    // Create the icon menu in the action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dashboard_menu, menu)
    }

    // SelectItem for menu we want create the page for it
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_settings -> {
                findNavController().navigate(R.id.action_dashBoardFragment_to_settingsFragment)
            }
            R.id.action_cart -> {
                findNavController().navigate(R.id.action_dashBoardFragment_to_cartListFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(
        viewHolder: RecyclerDashBoardAdapter.ViewHolder,
        dataSet: ProductModel,
        position: Int
    ) {

        viewHolder.itemView.setOnClickListener {
            var action =
                DashBoardFragmentDirections.actionDashBoardFragmentToProductDetailsFragment(dataSet)
            findNavController().navigate(action)
        }
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val callback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                Toast.makeText(requireActivity() , "Exit", Toast.LENGTH_SHORT).show()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
//    }
}