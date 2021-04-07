package com.example.yshop.cartlistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerCartListAdapter
import com.example.yshop.adapter.RecyclerDashBoardAdapter
import com.example.yshop.databinding.FragmentCartListBinding
import com.example.yshop.model.CartItemModel
import com.example.yshop.model.ProductModel
import com.example.yshop.utils.OptionBuilder

class CartListFragment : Fragment() , RecyclerCartListAdapter.OnClickCartList{

    lateinit var binding    : FragmentCartListBinding
    val cartListViewModel   : CartListViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.cartListVarModel = cartListViewModel

        // Back to dash board by back icon in tool bar
        binding.toolbarCartListFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarCartListFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_cartListFragment_to_dashBoardFragment)
        }

        cartListViewModel.getCartItemList( binding.rvCartItemsList ,
                binding.llCheckout ,
                binding.tvNoCartItemFound ,
                binding.tvSubTotal ,
                binding.tvShippingCharge ,
                binding.tvTotalAmount)

        // call function cart list item
        OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
        cartListViewModel.cartItemModel.observe(viewLifecycleOwner , Observer {
            binding.rvCartItemsList.adapter = RecyclerCartListAdapter(it , this)
            OptionBuilder.hideProgressDialog()
        })
    }

    override fun onClick(viewHolder: RecyclerCartListAdapter.ViewHolder, dataSet: CartItemModel, position: Int) {

        // Check cartQuantity available
        if( dataSet.cartQuantity == "0"){
            viewHolder.binding.ibRemoveCartItem.visibility  = View.GONE
            viewHolder.binding.ibAddCartItem.visibility     = View.GONE

            viewHolder.binding.tvCartQuantity.text = resources.getString(R.string.lbl_out_of_stock)
            viewHolder.binding.tvCartQuantity.setTextColor(ContextCompat.getColor(requireActivity() , R.color.colorSnackBarError))
        }else{
            viewHolder.binding.ibRemoveCartItem.visibility  = View.VISIBLE
            viewHolder.binding.ibAddCartItem.visibility     = View.VISIBLE
        }

        // Delete item from cart list
        viewHolder.binding.ibDeleteCartItem.setOnClickListener {
            cartListViewModel.removeItemFromCart( requireActivity() , dataSet.id)

        }

        // Add new quantity
        viewHolder.binding.ibAddCartItem.setOnClickListener {
            cartListViewModel.plusCartItem(dataSet.id , dataSet.cartQuantity , dataSet.stockQuantity )
        }

        // Remove Quantity
        viewHolder.binding.ibRemoveCartItem.setOnClickListener {
            cartListViewModel.minusCartItem( requireActivity() , dataSet.id , dataSet.cartQuantity)

        }


    }
}