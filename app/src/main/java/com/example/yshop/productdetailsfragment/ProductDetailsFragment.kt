package com.example.yshop.productdetailsfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yshop.R
import com.example.yshop.databinding.FragmentProductDetailsBinding
import com.example.yshop.model.CartItemModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

    lateinit var binding                : FragmentProductDetailsBinding
    private val productDetailsViewModel : ProductDetailsViewModel by viewModels()
    val product                         : ProductDetailsFragmentArgs by navArgs()
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

        // Check entry from product fragment to product fragment details by extra product id key pass from product fragment by bundle
        if(arguments?.containsKey(Constants.EXTRA_PRODUCT_ID) == true){
           mProductId = arguments?.getString(Constants.EXTRA_PRODUCT_ID).toString()

            // Show details for product select by product id from product fragment
            productDetailsViewModel.getProductDetails( requireActivity() ,
                    mProductId ,
                    binding.tvNameAddProduct,
                    binding.tvProductDetailsTitle ,
                    binding.tvProductDetailsPrice ,
                    binding.tvProductDetailsDescription ,
                    binding.tvProductDetailsStockQuantity ,
                    binding.ivProductDetailImage)

            // Navigation back button in tool bar for back to product fragment
            binding.toolbarProductDetailsFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
            binding.toolbarProductDetailsFragment.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_productDetailsFragment_to_productsFragment)
            }
        }


        // Check entry to product details from dashboard by extra user id key pass from product fragment by bundle
        var productOwnerId : String = ""
        if(arguments?.containsKey(Constants.EXTRA_PRODUCT_OWNER_ID) == true){
            productOwnerId = arguments?.getString(Constants.EXTRA_PRODUCT_OWNER_ID).toString()

        }
        // Check user id already log in equal extra user id pass from product fragment
        if(Constants.getCurrentUser() == productOwnerId){

            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        }else{


            // Show details for product select from dashboard fragment
            binding.tvProductDetailsTitle.text          = product.productItem.title
            binding.tvProductDetailsPrice.text          = "$${product.productItem.price}"
            binding.tvProductDetailsDescription.text    = product.productItem.description
            binding.tvProductDetailsStockQuantity.text  = product.productItem.stockQuantity
            binding.tvNameAddProduct.text               = product.productItem.userName
            Picasso.get().load(product.productItem.productImage).into(binding.ivProductDetailImage)

            // check of stock quantity where quantity of product equal 0 will GONE button for add to cart and show message out of stock
            if( product.productItem.stockQuantity.toInt() == 0){
                binding.btnAddToCart.visibility = View.GONE
                binding.tvProductDetailsStockQuantity.text = resources.getString(R.string.lbl_out_of_stock)
                binding.tvProductDetailsStockQuantity.setTextColor(ContextCompat.getColor(requireActivity() , R.color.colorSnackBarError))
            }else{
                // Check user add new product where this user log in will GONE  button add cart and button go cart
                if( Constants.getCurrentUser() == product.productItem.userId){
                    binding.btnAddToCart.visibility = View.GONE
                }else{
                    // Show add to cart button
                    binding.btnAddToCart.visibility = View.VISIBLE
                }
            }

            // Navigation back button in tool bar for back to dashboard fragment
            binding.toolbarProductDetailsFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
            binding.toolbarProductDetailsFragment.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_productDetailsFragment_to_dashBoardFragment)
            }
            // Check the item add to cart or no when add to cart will Gone button add to card and show button go to cart page
            productDetailsViewModel.checkIfItemExistInCart( product.productItem.productId , binding.btnAddToCart , binding.btnGoToCart)
        }


        // Add to cart
        binding.btnAddToCart.setOnClickListener {
            val cartItem = CartItemModel(
                    Constants.getCurrentUser(),
                    product.productItem.productId,
                    product.productItem.title,
                    product.productItem.price,
                    product.productItem.productImage,
                    Constants.DEFAULT_CART_ITEM
            )
            // Show progress dialog
            OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
            productDetailsViewModel.addCartItem(cartItem )
            // Hide progress dialog
            OptionBuilder.hideProgressDialog()
            Toast.makeText(requireActivity() , resources.getString(R.string.success_message_item_added_to_cart),Toast.LENGTH_SHORT).show()
        }

        // Go to cart page
        binding.btnGoToCart.setOnClickListener {
            findNavController().navigate(R.id.action_productDetailsFragment_to_cartListFragment)
        }
    }
}