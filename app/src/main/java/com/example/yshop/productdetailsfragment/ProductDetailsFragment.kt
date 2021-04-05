package com.example.yshop.productdetailsfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yshop.dashboardfragment.DashBoardFragment
import com.example.yshop.databinding.FragmentProductDetailsBinding
import com.example.yshop.model.ProductModel
import com.example.yshop.productsfargment.ProductsFragment
import com.example.yshop.utils.Constants
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

    lateinit var binding        : FragmentProductDetailsBinding
    val productDetailsViewModel : ProductDetailsViewModel by viewModels()
    val product                 : ProductDetailsFragmentArgs by navArgs()
    // A global variable for product id.
    var mProductId : String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit viewModel
        binding.lifecycleOwner = this
        binding.productVarModel = productDetailsViewModel


        // Operation for show data
        if(arguments?.containsKey(Constants.EXTRA_PRODUCT_ID) == true){
           mProductId = arguments?.getString(Constants.EXTRA_PRODUCT_ID).toString()

        }
        productDetailsViewModel.getProductDetails( mProductId ,
                binding.tvProductDetailsTitle ,
                binding.tvProductDetailsPrice ,
                binding.tvProductDetailsDescription ,
                binding.tvProductDetailsStockQuantity ,
                binding.ivProductDetailImage)



        var productOwnerId : String = ""
        if(arguments?.containsKey(Constants.EXTRA_PRODUCT_OWNER_ID) == true){
            productOwnerId = arguments?.getString(Constants.EXTRA_PRODUCT_OWNER_ID).toString()
        }
        if(Constants.getCurrentUser() == productOwnerId){
            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        }else{
            binding.btnAddToCart.visibility = View.VISIBLE
        }


//        binding.tvProductDetailsTitle.text          = product.peoductItem.title
//        binding.tvProductDetailsPrice.text          = product.peoductItem.price
//        binding.tvProductDetailsDescription.text    = product.peoductItem.description
//        binding.tvProductDetailsStockQuantity.text  = product.peoductItem.stockQuantity
//        Picasso.get().load(product.peoductItem.productImage).into(binding.ivProductDetailImage)

    }
}