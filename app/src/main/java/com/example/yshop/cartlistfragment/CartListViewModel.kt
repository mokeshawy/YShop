package com.example.yshop.cartlistfragment


import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.model.CartItemModel
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartListViewModel : ViewModel() {

    var cartItemModel = MutableLiveData<ArrayList<CartItemModel>>()
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var cartItemReference = firebaseDatabase.getReference(Constants.CART_ITEM)


    fun getCartItemList( rv_cart_items_list : RecyclerView
                         , ll_checkout : LinearLayout ,
                         tv_no_cart_item_found : TextView ,
                         tv_sub_total : TextView ,
                         tv_shipping_charge : TextView ,
                         tv_total_amount : TextView){

        var array : ArrayList<CartItemModel> = ArrayList()

        cartItemReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( ds in snapshot.children ){

                    var cartItem = ds.getValue(CartItemModel::class.java)!!
                    cartItem.id = ds.key.toString()
                    array.add(cartItem)
                }
                cartItemModel.value = array
                if( cartItemModel.value!!.size > 0){
                    rv_cart_items_list.visibility       = View.VISIBLE
                    ll_checkout.visibility              = View.VISIBLE
                    tv_no_cart_item_found.visibility    = View.GONE

                    var subTotal : Double = 0.0
                    for( item in cartItemModel.value!!){

                        var price       = item.price.toDouble()
                        var quantity    = item.cartQuantity.toInt()

                        subTotal += ( price * quantity)
                    }
                    tv_sub_total.text = "$${subTotal}"
                    tv_shipping_charge.text = "$10.0"
                    if( subTotal > 0 ){
                        ll_checkout.visibility = View.VISIBLE
                        var total = subTotal + 10
                        tv_total_amount.text = "$${total}"
                    }else{
                        ll_checkout.visibility = View.GONE
                    }
                }else {

                    rv_cart_items_list.visibility       = View.GONE
                    ll_checkout.visibility              = View.GONE
                    tv_no_cart_item_found.visibility    = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}