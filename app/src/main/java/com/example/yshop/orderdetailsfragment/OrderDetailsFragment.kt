package com.example.yshop.orderdetailsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerCartListAdapter
import com.example.yshop.databinding.FragmentOrderDetailsBinding
import com.example.yshop.model.CartItemModel
import com.example.yshop.model.OrderModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

class OrderDetailsFragment : Fragment() , RecyclerCartListAdapter.OnClickCartList{

    lateinit var binding                : FragmentOrderDetailsBinding
    private val orderDetailsViewModel   : OrderDetailsViewModel by viewModels()
    var mOrderDetails = OrderModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner          = this
        binding.orderDetailsVarModel    = orderDetailsViewModel

        // Check argument pass from orderFragment
        if( arguments?.containsKey(Constants.EXTRA_MY_ORDER_DETAILS) == true){
            mOrderDetails = arguments?.getSerializable(Constants.EXTRA_MY_ORDER_DETAILS) as OrderModel
        }

        orderDetailsViewModel.setUpUi( requireActivity() ,
            mOrderDetails ,
            binding.tvOrderDetailsId ,
            binding.tvOrderDetailsDate ,
            binding.tvOrderStatus ,
            binding.tvMyOrderDetailsAddressType ,
            binding.tvMyOrderDetailsFullName ,
            binding.tvMyOrderDetailsAddress ,
            binding.tvMyOrderDetailsAdditionalNote ,
            binding.tvMyOrderDetailsOtherDetails ,
            binding.tvMyOrderDetailsMobileNumber ,
            binding.tvOrderDetailsSubTotal ,
            binding.tvOrderDetailsShippingCharge ,
            binding.tvOrderDetailsTotalAmount)

        OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
        // Show data from cart items
        orderDetailsViewModel.cartListAdapter.observe(viewLifecycleOwner , Observer {
            binding.rvMyOrderItemsList.adapter = RecyclerCartListAdapter( it , this , false)
            OptionBuilder.hideProgressDialog()
        })
    }

    override fun onClick( updateCartItems: Boolean, viewHolder: RecyclerCartListAdapter.ViewHolder, dataSet: CartItemModel, position: Int ) {
        // Check updateCartItem equal false
        if(updateCartItems){
            viewHolder.binding.ibDeleteCartItem.visibility  = View.VISIBLE
            viewHolder.binding.ibAddCartItem.visibility     = View.VISIBLE
            viewHolder.binding.ibRemoveCartItem.visibility  = View.VISIBLE
        }else{
            viewHolder.binding.ibDeleteCartItem.visibility = View.GONE
            viewHolder.binding.ibAddCartItem.visibility    = View.GONE
            viewHolder.binding.ibRemoveCartItem.visibility = View.GONE
        }
    }
}