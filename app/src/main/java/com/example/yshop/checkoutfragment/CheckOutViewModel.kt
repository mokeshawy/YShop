package com.example.yshop.checkoutfragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.R
import com.example.yshop.model.*
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CheckOutViewModel : ViewModel() {

    var mCartItemList       = MutableLiveData<ArrayList<CartItemModel>>()
    var mProductList        = MutableLiveData<ArrayList<ProductModel>>()
    var firebaseDatabase    = FirebaseDatabase.getInstance()
    var cartItemReference   = firebaseDatabase.getReference(Constants.CART_ITEM)
    var productReference    = firebaseDatabase.getReference(Constants.PRODUCT)
    var orderReference      = firebaseDatabase.getReference(Constants.ORDER_REF)
    var soldProductReference = firebaseDatabase.getReference(Constants.SOLD_PRODUCT_REF)


    var cartListArray           : ArrayList<CartItemModel> = ArrayList()
    var productList             : ArrayList<ProductModel> = ArrayList()


    private var subTotal        : Double = 0.0
    private var mTotalAmount    : Double = 0.0
    private var mOrderDetails   : OrderModel = OrderModel()
    // fun get cart list show in check out page
    fun getCartItemList(ll_checkout_place_order : LinearLayout,
                        tv_checkout_sub_total : TextView,
                        tv_checkout_shipping_charge : TextView,
                        tv_checkout_total_amount : TextView){

        // Call get all data from product reference
        getAllProductData()

        cartItemReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                cartListArray.clear()
                for( ds in snapshot.children ){

                    var cartItem = ds.getValue(CartItemModel::class.java)!!
                    cartItem.id = ds.key.toString()
                    cartListArray.add(cartItem)
                }
                // get data form productList
                for( product in mProductList.value!!){
                    for( cart in cartListArray){
                        if( product.productId == cart.productId){
                            cart.stockQuantity = product.stockQuantity
                        }
                    }
                }
                mCartItemList.value = cartListArray

                for( item in mCartItemList.value!!){
                    // Check quantity in cart reference
                    if( item.stockQuantity.toInt() > 0){

                        var price    = item.price.toDouble()
                        var quantity = item.cartQuantity.toInt()

                        subTotal     += ( price * quantity)
                    }
                }
                tv_checkout_sub_total.text       = "$${subTotal}"
                tv_checkout_shipping_charge.text = "$10.0"
                if( subTotal > 0 ){

                    ll_checkout_place_order.visibility = View.VISIBLE

                   mTotalAmount = subTotal + 10

                    tv_checkout_total_amount.text = "$${mTotalAmount}"

                }else{
                    ll_checkout_place_order.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // Fun get all product list
    private fun getAllProductData(){

        productReference.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( ds in snapshot.children ){

                    var product = ds.getValue(ProductModel::class.java)!!

                    product.productId = ds.key.toString()

                    productList.add(product)
                }
                mProductList.value = productList
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    // Fun place order
    private fun placeAnOrder( checkOutFragment: CheckOutFragment){
        var mAddressDetails = checkOutFragment.arguments?.getSerializable(Constants.EXTRA_SELECTED_ADDRESS) as AddressModel
        mOrderDetails = OrderModel(
                Constants.getCurrentUser(),
                cartListArray,
                mAddressDetails,
                "My Order ${System.currentTimeMillis()}",
                cartListArray[0].image,
                subTotal.toString(),
                "10.0",
                mTotalAmount.toString(),
                System.currentTimeMillis()
        )
        orderReference.push().setValue(mOrderDetails)
        Toast.makeText(checkOutFragment.requireActivity() , "Your order was placed Successfully",Toast.LENGTH_SHORT).show()
        checkOutFragment.findNavController().navigate(R.id.action_checkOutFragment_to_dashBoardFragment)
    }

    // Fun update all data after click on place order button
    fun updateAllData( checkOutFragment: CheckOutFragment){

        // Call place an order function
        placeAnOrder(checkOutFragment)

        // Prepare the sold product details
        for( cartItem in cartListArray){

            val soldProduct = SoldProductModel(
                   Constants.getCurrentUser(),
                    cartItem.title,
                    cartItem.price,
                    cartItem.cartQuantity,
                    cartItem.image,
                    mOrderDetails.title,
                    mOrderDetails.orderDataTime,
                    mOrderDetails.subTotalAmount,
                    mOrderDetails.shippingCharge,
                    mOrderDetails.totalAmount,
                    mOrderDetails.address
            )
            soldProductReference.child(cartItem.productId).setValue(soldProduct)

        }

        // Here we will update the product stock in the products collection based to cart quantity.
        for( cartItem in cartListArray){

            var productMap = HashMap<String , Any>()

            productMap[Constants.PRODUCT_QUANTITY] =
                    ( cartItem.stockQuantity.toInt() - cartItem.cartQuantity.toInt() ).toString()

            productReference.child(cartItem.productId).updateChildren(productMap)
        }

        // Delete the list of cart items
        for(cartItem in cartListArray){
            cartItemReference.child(cartItem.id).removeValue()
        }
    }
}