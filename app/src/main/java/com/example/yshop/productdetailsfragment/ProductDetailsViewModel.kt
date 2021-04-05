package com.example.yshop.productdetailsfragment

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.yshop.model.ProductModel
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProductDetailsViewModel : ViewModel() {

    var fireBaseDatabase = FirebaseDatabase.getInstance()
    var productReference = fireBaseDatabase.getReference(Constants.PRODUCT)

    fun getProductDetails( productId : String ,
                          tv_product_title : TextView ,
                          tv_product_price : TextView ,
                          tv_product_description : TextView ,
                          tv_product_stock_quantity : TextView ,
                          iv_product_image : ImageView){

        productReference.child(productId).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var title       = snapshot.child("title").value.toString()
                var price       = snapshot.child("price").value.toString()
                var desc        = snapshot.child("description").value.toString()
                var quantity    = snapshot.child("stockQuantity").value.toString()
                var image       = snapshot.child("productImage").value.toString()

                tv_product_title.text = title
                tv_product_price.text = price
                tv_product_description.text = desc
                tv_product_stock_quantity.text = quantity
                Picasso.get().load(image).into(iv_product_image)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}