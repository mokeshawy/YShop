package com.example.yshop.feature.dashboardfragment.domain.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.core.utils.Constants
import com.example.yshop.feature.dashboardfragment.data.model.request.ProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashBoardFragmentViewModel : ViewModel() {


    var getProductDetails = MutableLiveData<ArrayList<ProductModel>>()
    var firebaseDataBase = FirebaseDatabase.getInstance()
    var productReference = firebaseDataBase.getReference(Constants.PRODUCT)
    var array = ArrayList<ProductModel>()

    fun getProductDetails(rv_dashboard_items: RecyclerView, tv_no_dashboard_items_found: TextView) {
        array = ArrayList()
        productReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {

                    var id = ds.key
                    val product = ds.getValue(ProductModel::class.java)
                    product!!.productId = id.toString()
                    array.add(product)
                }
                getProductDetails.value = array

                if (array.size > 0) {
                    rv_dashboard_items.visibility = View.VISIBLE
                    tv_no_dashboard_items_found.visibility = View.GONE

                    rv_dashboard_items.setHasFixedSize(true)


                } else {
                    rv_dashboard_items.visibility = View.GONE
                    tv_no_dashboard_items_found.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}