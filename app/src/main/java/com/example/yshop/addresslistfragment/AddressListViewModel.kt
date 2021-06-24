package com.example.yshop.addresslistfragment

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerAddressAdapter
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.myshoppal.utils.SwipeToDeleteCallback
import com.myshoppal.utils.SwipeToEditCallback
import java.text.FieldPosition

class AddressListViewModel : ViewModel(){


    var addressList      = MutableLiveData<ArrayList<AddressModel>>()
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var addressReference = firebaseDatabase.getReference(Constants.ADD_ADDRESS_REF)

    fun getAddressList( context: Context ,
                        rv_address_list : RecyclerView ,
                        tv_no_address_found : TextView ,
                        fragmentAddressList : AddressListFragment ){

        var addressArray : ArrayList<AddressModel> = ArrayList()
        addressReference.orderByChild(Constants.USER_ID).equalTo(Constants.getCurrentUser()).addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                addressArray.clear()
                for ( ds in snapshot.children ){

                    var addressItemList = ds.getValue(AddressModel::class.java)!!
                    addressItemList.id  = ds.key.toString()

                    addressArray.add(addressItemList)
                }
                addressList.value = addressArray

                if( addressList.value!!.size > 0){
                    rv_address_list.visibility      = View.VISIBLE
                    tv_no_address_found.visibility  = View.GONE

                    // Check when entry from setting page will show edit and swipe delete address and entry from add cart will not show swipe edit and delete
                    if (fragmentAddressList.arguments?.containsKey(Constants.EXTRA_ADDRESS_DETAILS) != true){

                        // Make the swipe edit on address item
                        val editSwipeHandler = object : SwipeToEditCallback(context){
                            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                                OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait) , context)
                                val adapter = rv_address_list.adapter as RecyclerAddressAdapter
                                adapter.notifyEditItem( fragmentAddressList , viewHolder.adapterPosition)
                                OptionBuilder.hideProgressDialog()
                            }
                        }
                        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
                        editItemTouchHelper.attachToRecyclerView(rv_address_list)

                        // Delete address swipe handler
                        val deleteSwipeHandler = object : SwipeToDeleteCallback(context){
                            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                                OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait) , context)
                                addressReference.child(addressArray[viewHolder.adapterPosition].id).removeValue()
                                OptionBuilder.hideProgressDialog()
                                Toast.makeText(context , context.resources.getString(R.string.err_your_address_deleted_successfully), Toast.LENGTH_SHORT).show()
                            }
                        }
                        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
                        deleteItemTouchHelper.attachToRecyclerView(rv_address_list)
                    }
                }else{
                    rv_address_list.visibility      = View.GONE
                    tv_no_address_found.visibility  = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}