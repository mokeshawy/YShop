package com.example.yshop.soldproductdetailsfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentSoldProductDetailsBinding
import com.example.yshop.model.SoldProductModel
import com.example.yshop.utils.Constants


class SoldProductDetailsFragment : Fragment() {

    lateinit var binding : FragmentSoldProductDetailsBinding
    private val soldProductDetailsViewModel : SoldProductDetailsViewModel by viewModels()
    var mSoldProduct = SoldProductModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSoldProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner      = this
        binding.soldProductDetailsVarModel = soldProductDetailsViewModel


        // Check pass data from soldProduct fragment
        if( arguments?.containsKey(Constants.EXTRA_SOLD_PRODUCT_DETAILS) == true){
            mSoldProduct = arguments?.getSerializable( Constants.EXTRA_SOLD_PRODUCT_DETAILS) as SoldProductModel
        }

        soldProductDetailsViewModel.setUpUi(mSoldProduct ,
                binding.tvSoldProductDetailsId ,
                binding.tvSoldProductDetailsDate ,
                binding.ivProductItemImage,
                binding.tvProductItemName ,
                binding.tvProductItemPrice ,
                binding.tvSoldProductQuantity ,
                binding.tvSoldDetailsAddressType ,
                binding.tvSoldDetailsFullName ,
                binding.tvSoldDetailsAddress ,
                binding.tvSoldDetailsAdditionalNote ,
                binding.tvSoldDetailsOtherDetails ,
                binding.tvSoldDetailsMobileNumber ,
                binding.tvSoldProductSubTotal ,
                binding.tvSoldProductShippingCharge ,
                binding.tvSoldProductTotalAmount )
    }
}