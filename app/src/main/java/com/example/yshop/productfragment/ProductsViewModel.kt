package com.example.yshop.productsfargment

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.R
import com.example.yshop.model.ProductModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductsViewModel : ViewModel() {


    var getProductDetails   = MutableLiveData<ArrayList<ProductModel>>()
    var firebaseDataBase    = FirebaseDatabase.getInstance()
    var productReference    = firebaseDataBase.getReference(Constants.PRODUCT)
    var getProductArray     = ArrayList<ProductModel>()

    fun getProductDetailsById( rv_product_items : RecyclerView , tv_no_products_found : TextView ){
     getProductArray = ArrayList()
        productReference.orderByChild(Constants.PRODUCT_USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                getProductArray.clear()
                for (ds in snapshot.children){

                    var id = ds.key
                    val product = ds.getValue(ProductModel::class.java)
                    product!!.productId = id.toString()
                    getProductArray.add(product)
                }
                getProductDetails.value = getProductArray
                if(getProductArray.size > 0){
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

    fun deleteProduct( productId : String , productTitle : String ,  context: Context ){
        var alert = AlertDialog.Builder(context)
        alert.setTitle(context.resources.getString(R.string.delete_dialog_title))
        alert.setMessage("${context.resources.getString(R.string.delete_dialog_message)}${productTitle}")
        alert.setPositiveButton(context.resources.getString(R.string.yes)){dialog, which ->

            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)
            productReference.child(productId).removeValue()
            Toast.makeText(context , "${context.resources.getString(R.string.err_your_address_deleted_successfully)}${productTitle}",Toast.LENGTH_SHORT).show()
            OptionBuilder.hideProgressDialog()
        }
        alert.setNegativeButton(context.resources.getString(R.string.no),null)
        alert.create().show()
    }
}
