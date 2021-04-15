package com.example.yshop.checkoutfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerCartListAdapter
import com.example.yshop.databinding.FragmentCheckOutBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.model.CartItemModel
import com.example.yshop.productsfargment.ProductsFragment
import com.example.yshop.utils.Constants

class CheckOutFragment : Fragment() , RecyclerCartListAdapter.OnClickCartList {

    lateinit var binding            : FragmentCheckOutBinding
    private val checkOutViewModel   : CheckOutViewModel by viewModels()
    private var mAddressDetails     : AddressModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckOutBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner      = this
        binding.checkOutVarModel    = checkOutViewModel

        // Check object pass from AddListAddress
        if(arguments?.containsKey(Constants.EXTRA_SELECTED_ADDRESS) == true){
            mAddressDetails = arguments?.getSerializable(Constants.EXTRA_SELECTED_ADDRESS) as AddressModel?
        }

        if( mAddressDetails != null){

            binding.tvCheckoutAddressType.text      = mAddressDetails?.type
            binding.tvCheckoutFullName.text         = mAddressDetails?.name
            binding.tvCheckoutAddress.text          = "${mAddressDetails?.address}, ${mAddressDetails?.zipCode}"
            binding.tvCheckoutAdditionalNote.text   = mAddressDetails?.additionalNote

            if(mAddressDetails?.otherDetails!!.isNotEmpty()){
                binding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            binding.tvCheckoutMobileNumber.text     = mAddressDetails?.mobileNumber
        }

        checkOutViewModel.getCartItemList(binding.llCheckoutPlaceOrder , binding.tvCheckoutSubTotal , binding.tvCheckoutShippingCharge , binding.tvCheckoutTotalAmount)
        checkOutViewModel.mCartItemList.observe(viewLifecycleOwner , Observer {
            binding.rvCartListItems.adapter = RecyclerCartListAdapter(it , this , false)
        })

        binding.btnPlaceOrder.setOnClickListener {
            checkOutViewModel.updateAllData(this)
        }
    }

    override fun onClick( updateCartItems : Boolean , viewHolder: RecyclerCartListAdapter.ViewHolder, dataSet: CartItemModel, position: Int) {

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