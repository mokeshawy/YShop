package com.example.yshop.productsfargment

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.model.ProductModel
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductsViewModel : ViewModel() {


    var getProductDetails   = MutableLiveData<ArrayList<ProductModel>>()
    var firebaseDataBase    = FirebaseDatabase.getInstance()
    var productReference    = firebaseDataBase.getReference(Constants.PRODUCT)
    var array = ArrayList<ProductModel>()

    fun getProductDetailsById( rv_product_items : RecyclerView , tv_no_products_found : TextView ){
     array = ArrayList()
        productReference.orderByChild(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children){

                    var id = ds.key
                    val product = ds.getValue(ProductModel::class.java)
                    product!!.productId = id.toString()
                    array.add(product)
                }
                getProductDetails.value = array
                if(array.size > 0){
                    rv_product_items.visibility     = View.VISIBLE
                    tv_no_products_found.visibility = View.GONE

                    rv_product_items.setHasFixedSize(true)

                }else{
                    rv_product_items.visibility     = View.GONE
                    tv_no_products_found.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun deleteProduct( productId : String ){

        productReference.child(productId).removeValue()
    }


}
