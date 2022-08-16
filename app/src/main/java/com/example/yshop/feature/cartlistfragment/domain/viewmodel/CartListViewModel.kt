package com.example.yshop.feature.cartlistfragment.domain.viewmodel


import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.R
import com.example.yshop.core.utils.Constants
import com.example.yshop.core.utils.OptionBuilder
import com.example.yshop.feature.dashboardfragment.data.model.request.ProductModel
import com.example.yshop.feature.domain.model.CartItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartListViewModel : ViewModel() {

    var cartItemList = MutableLiveData<ArrayList<CartItemModel>>()
    var mProductList = MutableLiveData<ArrayList<ProductModel>>()
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var cartItemReference = firebaseDatabase.getReference(Constants.CART_ITEM)
    var productReference = firebaseDatabase.getReference(Constants.PRODUCT)

    fun getCartItemList(
        rv_cart_items_list: RecyclerView,
        ll_checkout: LinearLayout,
        tv_no_cart_item_found: TextView,
        tv_sub_total: TextView,
        tv_shipping_charge: TextView,
        tv_total_amount: TextView
    ) {

        // Call get all data from product reference
        getAllProductData()

        var cartListArray: ArrayList<CartItemModel> = ArrayList()
        cartItemReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartListArray.clear()
                    for (ds in snapshot.children) {

                        var cartItem = ds.getValue(CartItemModel::class.java)!!
                        cartItem.id = ds.key.toString()
                        cartListArray.add(cartItem)
                    }
                    // get data form productList
                    for (product in mProductList.value!!) {
                        for (cart in cartListArray) {
                            if (product.productId == cart.productId) {

                                cart.stockQuantity = product.stockQuantity

                                if (product.stockQuantity.toInt() == 0) {
                                    cart.cartQuantity = product.stockQuantity
                                }
                            }
                        }
                    }
                    cartItemList.value = cartListArray

                    if (cartItemList.value!!.size > 0) {
                        rv_cart_items_list.visibility = View.VISIBLE
                        ll_checkout.visibility = View.VISIBLE
                        tv_no_cart_item_found.visibility = View.GONE

                        var subTotal: Double = 0.0
                        for (item in cartItemList.value!!) {
                            // Check quantity in cart reference
                            if (item.stockQuantity.toInt() > 0) {

                                var price = item.price.toDouble()
                                var quantity = item.cartQuantity.toInt()

                                subTotal += (price * quantity)
                            }
                        }
                        tv_sub_total.text = "$${subTotal}"
                        tv_shipping_charge.text = "$10.0"
                        if (subTotal > 0) {
                            ll_checkout.visibility = View.VISIBLE
                            var total = subTotal + 10
                            tv_total_amount.text = "$${total}"
                        } else {
                            ll_checkout.visibility = View.GONE
                        }
                    } else {

                        rv_cart_items_list.visibility = View.GONE
                        ll_checkout.visibility = View.GONE
                        tv_no_cart_item_found.visibility = View.VISIBLE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun getAllProductData() {

        var productList: ArrayList<ProductModel> = ArrayList()
        productReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {

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

    // fun remove item from cart
    fun removeItemFromCart(context: Context, cartId: String) {
        OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait), context)
        cartItemReference.child(cartId).removeValue()
        Toast.makeText(
            context,
            context.resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()
    }

    // Minus cart item
    fun minusCartItem(context: Context, cartId: String, cartQuantity: String) {
        if (cartQuantity.toInt() == 1) {
            removeItemFromCart(context, cartId)
        } else {
            var cartItemQuantity: Int = cartQuantity.toInt()
            var map = HashMap<String, Any>()
            map[Constants.CART_QUANTITY] = (cartItemQuantity - 1).toString()
            cartItemReference.child(cartId).updateChildren(map)

        }
    }

    // Plus item in cart list
    fun plusCartItem(
        context: Context,
        cartId: String,
        cartQuantity: String,
        cartStockQuantity: String,
        view: View
    ) {
        var cartItemQuantity = cartQuantity.toInt()
        if (cartItemQuantity < cartStockQuantity.toInt()) {
            var map = HashMap<String, Any>()
            map[Constants.CART_QUANTITY] = (cartItemQuantity + 1).toString()
            cartItemReference.child(cartId).updateChildren(map)
        } else {
            OptionBuilder.showErrorSnackBar(
                context.resources.getString(
                    R.string.msg_for_available_stock,
                    cartStockQuantity
                ), true, context, view
            )
        }
    }
}