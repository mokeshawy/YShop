package com.example.yshop.soldproductfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerSoldProductAdapter
import com.example.yshop.databinding.FragmentSoldProductBinding
import com.example.yshop.model.SoldProductModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

class SoldProductFragment : Fragment() , RecyclerSoldProductAdapter.OnClickSoldProduct {

    lateinit var binding: FragmentSoldProductBinding
    private val soldProductViewModel : SoldProductViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSoldProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner      = this
        binding.soldProductVarModel = soldProductViewModel



        OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
        // Show data from soldProduct reference
        soldProductViewModel.getSoldProductList( binding.rvSoldProductItems , binding.tvNoSoldProductsFound)
        soldProductViewModel.mSoldProduct.observe(viewLifecycleOwner , Observer {
            binding.rvSoldProductItems.adapter = RecyclerSoldProductAdapter( it , this)
            OptionBuilder.hideProgressDialog()
        })


    }


    override fun onClick(viewHolder: RecyclerSoldProductAdapter.ViewHolder, dataSet: SoldProductModel, position: Int) {
        viewHolder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable( Constants.EXTRA_SOLD_PRODUCT_DETAILS , dataSet)
            findNavController().navigate(R.id.action_soldProductFragment_to_soldProductDetailsFragment , bundle)
        }
    }
}