package com.example.yshop.feature.ordersfragment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.core.utils.Constants
import com.example.yshop.core.utils.OptionBuilder
import com.example.yshop.databinding.FragmentOrdersBinding
import com.example.yshop.feature.adapter.RecyclerOrderAdapter
import com.example.yshop.feature.domain.model.OrderModel
import com.example.yshop.feature.ordersfragment.domain.viewmodel.OrdersFragmentViewModel

class OrdersFragment : Fragment(), RecyclerOrderAdapter.OnClickOrder {

    lateinit var binding: FragmentOrdersBinding
    private val ordersViewModel: OrdersFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrdersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit viewModel
        binding.lifecycleOwner = this
        binding.ordersVarModel = ordersViewModel

        OptionBuilder.showProgressDialog(
            resources.getString(R.string.please_wait),
            requireActivity()
        )
        ordersViewModel.getMyOrderList(binding.rvMyOrderItems, binding.tvNoOrdersFound)
        ordersViewModel.mOrderList.observe(viewLifecycleOwner, Observer {
            binding.rvMyOrderItems.adapter = RecyclerOrderAdapter(it, this)
            OptionBuilder.hideProgressDialog()
        })
    }

    override fun onClick(
        viewHolder: RecyclerOrderAdapter.ViewHolder,
        dataSet: OrderModel,
        position: Int
    ) {

        viewHolder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable(Constants.EXTRA_MY_ORDER_DETAILS, dataSet)
            findNavController().navigate(R.id.action_ordersFragment_to_orderDetailsFragment, bundle)
        }
    }
}