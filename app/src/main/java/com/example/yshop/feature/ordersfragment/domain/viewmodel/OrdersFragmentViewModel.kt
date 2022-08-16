package com.example.yshop.feature.ordersfragment.domain.viewmodel

import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.core.utils.Constants
import com.example.yshop.feature.domain.model.OrderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrdersFragmentViewModel : ViewModel(){

    var mOrderList          = MutableLiveData<ArrayList<OrderModel>>()
    var firebaseDatabase    = FirebaseDatabase.getInstance()
    var orderReference      = firebaseDatabase.getReference(Constants.ORDER_REF)
    var mOrderListArray     : ArrayList<OrderModel> = ArrayList()
    fun getMyOrderList( rv_my_order_items : RecyclerView , tv_no_orders_found : TextView){

        orderReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mOrderListArray.clear()
                for( ds in snapshot.children){

                    var orderList = ds.getValue(OrderModel::class.java)!!
                    orderList.id = ds.key.toString()

                    mOrderListArray.add(orderList)
                }
                mOrderList.value = mOrderListArray

                if( mOrderList.value!!.size > 0) {

                    rv_my_order_items.visibility = View.VISIBLE
                    tv_no_orders_found.visibility = View.GONE
                }else{
                    rv_my_order_items.visibility = View.GONE
                    tv_no_orders_found.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}