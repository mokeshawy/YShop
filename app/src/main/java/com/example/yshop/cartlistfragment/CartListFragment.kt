package com.example.yshop.cartlistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        })
        OptionBuilder.hideProgressDialog()
    }

    override fun onClick(viewHolder: RecyclerCartListAdapter.ViewHolder, dataSet: CartItemModel, position: Int) {

    }
}