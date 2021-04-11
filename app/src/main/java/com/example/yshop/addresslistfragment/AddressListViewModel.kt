package com.example.yshop.addresslistfragment

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.adapter.RecyclerAddressAdapter
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.myshoppal.utils.SwipeToEditCallback
import java.text.FieldPosition

class AddressListViewModel : ViewModel(){


    var addressList = MutableLiveData<ArrayList<AddressModel>>()

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var addressReference = firebaseDatabase.getReference(Constants.ADD_ADDRESS)

    fun getAddressList( context: Context , rv_address_list : RecyclerView , tv_no_address_found : TextView , fragmentAddressList : AddressListFragment ){

        var addressArray : ArrayList<AddressModel> = ArrayList()
        addressReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                addressArray.clear()
                for ( ds in snapshot.children ){

                    var addressItemList = ds.getValue(AddressModel::class.java)!!
                    addressItemList.id = ds.key.toString()

                    addressArray.add(addressItemList)
                }
                addressList.value = addressArray

                if( addressList.value!!.size > 0){
                    rv_address_list.visibility = View.VISIBLE
                    tv_no_address_found.visibility= View.GONE

                    // Make the swipe edit on item
                    val editSwipeHandler = object : SwipeToEditCallback(context){
                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                            val adapter = rv_address_list.adapter as RecyclerAddressAdapter
                            adapter.notifyEditItem( fragmentAddressList , viewHolder.adapterPosition)
                        }
                    }
                    val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
                    editItemTouchHelper.attachToRecyclerView(rv_address_list)

                }else{
                    rv_address_list.visibility = View.GONE
                    tv_no_address_found.visibility= View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}