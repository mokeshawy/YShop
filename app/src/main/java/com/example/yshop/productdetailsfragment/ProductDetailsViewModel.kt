package com.example.yshop.productdetailsfragment

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.yshop.R
import com.example.yshop.model.CartItemModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProductDetailsViewModel : ViewModel() {

    var fireBaseDatabase = FirebaseDatabase.getInstance()
    var productReference = fireBaseDatabase.getReference(Constants.PRODUCT)

    fun getProductDetails( context: Context , productId : String ,
                          tv_product_title : TextView ,
                          tv_product_price : TextView ,
                          tv_product_description : TextView ,
                          tv_product_stock_quantity : TextView ,
                          iv_product_image : ImageView){
        // Show progress dialog
        OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait) , context)
        productReference.child(productId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var title       = snapshot.child(Constants.PRODUCT_TITLE).value.toString()
                var price       = snapshot.child(Constants.PRODUCT_PRICE).value.toString()
                var desc        = snapshot.child(Constants.PRODUCT_DESC).value.toString()
                var quantity    = snapshot.child(Constants.PRODUCT_QUANTITY).value.toString()
                var image       = snapshot.child(Constants.PRODUCT_IMAGE).value.toString()

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

    var cartItemReference = fireBaseDatabase.getReference(Constants.CART_ITEM)
    fun addCartItem( cartItemModel : CartItemModel){
        cartItemReference.push().setValue(cartItemModel)
    }

}