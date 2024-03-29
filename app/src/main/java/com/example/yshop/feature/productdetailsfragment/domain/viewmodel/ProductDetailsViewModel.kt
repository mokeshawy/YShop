package com.example.yshop.feature.productdetailsfragment.domain.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.yshop.R
import com.example.yshop.core.utils.Constants
import com.example.yshop.core.utils.OptionBuilder
import com.example.yshop.feature.domain.model.CartItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProductDetailsViewModel : ViewModel() {

    var fireBaseDatabase = FirebaseDatabase.getInstance()
    var productReference = fireBaseDatabase.getReference(Constants.PRODUCT)

    fun getProductDetails( context: Context , productId : String ,
                           tv_user_name_add_product : TextView,
                          tv_product_title : TextView ,
                          tv_product_price : TextView ,
                          tv_product_description : TextView ,
                          tv_product_stock_quantity : TextView ,
                          iv_product_image : ImageView ){
        // Show progress dialog
        OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait) , context)
        productReference.child(productId).addValueEventListener( object : ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                var userName    = snapshot.child(Constants.PRODUCT_USER_NAME).value.toString()
                var title       = snapshot.child(Constants.PRODUCT_TITLE).value.toString()
                var price       = snapshot.child(Constants.PRODUCT_PRICE).value.toString()
                var desc        = snapshot.child(Constants.PRODUCT_DESC).value.toString()
                var quantity    = snapshot.child(Constants.PRODUCT_QUANTITY).value.toString()
                var image       = snapshot.child(Constants.PRODUCT_IMAGE).value.toString()


                tv_user_name_add_product.text = userName
                tv_product_title.text = title
                tv_product_price.text = "$${price}"
                tv_product_description.text = desc
                tv_product_stock_quantity.text = quantity
                Picasso.get().load(image).into(iv_product_image)

                // Hide progress
                OptionBuilder.hideProgressDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    // Add cartItem
    var cartItemReference = fireBaseDatabase.getReference(Constants.CART_ITEM)
    fun addCartItem( cartItemModel : CartItemModel){
        cartItemReference.push().setValue(cartItemModel)
    }

    // Check the item add to cart or no when add to cart will Gone button add to card and show button go to cart page
    fun checkIfItemExistInCart(productId: String , btn_add_to_card : Button , btn_go_to_card : Button){
        cartItemReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( ds in snapshot.children ){

                    var proId = ds.child(Constants.PRODUCT_ID).value.toString()
                    if( proId == productId){
                        if( snapshot.childrenCount > 0){
                            btn_add_to_card.visibility = View.GONE
                            btn_go_to_card.visibility = View.VISIBLE
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}