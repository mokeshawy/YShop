package com.example.yshop.feature.checkoutfragment.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yshop.core.utils.Constants
import com.example.yshop.databinding.FragmentCheckOutBinding
import com.example.yshop.feature.adapter.RecyclerCartListAdapter
import com.example.yshop.feature.addresslistfragment.data.model.request.AddressModel
import com.example.yshop.feature.checkoutfragment.domain.viewmodel.CheckOutViewModel
import com.example.yshop.feature.domain.model.CartItemModel

class CheckOutFragment : Fragment(), RecyclerCartListAdapter.OnClickCartList {

    lateinit var binding: FragmentCheckOutBinding
    private val checkOutViewModel: CheckOutViewModel by viewModels()
    private var mAddressDetails: AddressModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.checkOutVarModel = checkOutViewModel

        // Check object pass from AddListAddress
        if (arguments?.containsKey(Constants.EXTRA_SELECTED_ADDRESS) == true) {
            mAddressDetails =
                arguments?.getSerializable(Constants.EXTRA_SELECTED_ADDRESS) as AddressModel?
        }

        if (mAddressDetails != null) {

            binding.tvCheckoutAddressType.text = mAddressDetails?.type
            binding.tvCheckoutFullName.text = mAddressDetails?.name
            binding.tvCheckoutAddress.text =
                "${mAddressDetails?.address}, ${mAddressDetails?.zipCode}"
            binding.tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            binding.tvCheckoutMobileNumber.text = mAddressDetails?.mobileNumber
        }

        checkOutViewModel.getCartItemList(
            binding.llCheckoutPlaceOrder,
            binding.tvCheckoutSubTotal,
            binding.tvCheckoutShippingCharge,
            binding.tvCheckoutTotalAmount
        )
        checkOutViewModel.mCartItemList.observe(viewLifecycleOwner, Observer {
            binding.rvCartListItems.adapter = RecyclerCartListAdapter(it, this, false)
        })

        binding.btnPlaceOrder.setOnClickListener {
            checkOutViewModel.updateAllData(this)
        }
    }

    override fun onClick(
        updateCartItems: Boolean,
        viewHolder: RecyclerCartListAdapter.ViewHolder,
        dataSet: CartItemModel,
        position: Int
    ) {

        // Check updateCartItem equal false
        if (updateCartItems) {
            viewHolder.binding.ibDeleteCartItem.visibility = View.VISIBLE
            viewHolder.binding.ibAddCartItem.visibility = View.VISIBLE
            viewHolder.binding.ibRemoveCartItem.visibility = View.VISIBLE
        } else {
            viewHolder.binding.ibDeleteCartItem.visibility = View.GONE
            viewHolder.binding.ibAddCartItem.visibility = View.GONE
            viewHolder.binding.ibRemoveCartItem.visibility = View.GONE
        }


    }
}