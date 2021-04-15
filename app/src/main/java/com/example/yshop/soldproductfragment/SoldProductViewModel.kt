package com.example.yshop.soldproductfragment

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.model.SoldProductModel
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SoldProductViewModel : ViewModel() {

    var mSoldProduct            = MutableLiveData<ArrayList<SoldProductModel>>()
    var firebaseDatabase        = FirebaseDatabase.getInstance()
    var soldProductReference    = firebaseDatabase.getReference(Constants.SOLD_PRODUCT_REF)

    var soldProductList         : ArrayList<SoldProductModel> = ArrayList()


    // Get sold product list
    fun getSoldProductList( rv_sold_product_items : RecyclerView , tv_no_sold_products_found : TextView){

        soldProductReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                soldProductList.clear()
                for ( ds in snapshot.children ){

                    var soldProduct = ds.getValue(SoldProductModel::class.java)!!
                    soldProduct.id = ds.key.toString()

                    soldProductList.add(soldProduct)
                }
                mSoldProduct.value = soldProductList

                if(mSoldProduct.value!!.size > 0 ){

                    rv_sold_product_items.visibility = View.VISIBLE
                    tv_no_sold_products_found.visibility = View.GONE
                }else{
                    rv_sold_product_items.visibility = View.GONE
                    tv_no_sold_products_found.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}