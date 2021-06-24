package com.example.yshop.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.R
import com.example.yshop.addresslistfragment.AddressListFragment
import com.example.yshop.databinding.ItemListAddressBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

class RecyclerAddressAdapter (private val dataSet: ArrayList<AddressModel> , var onClickAddressList: OnClickAddressList ) : RecyclerView.Adapter<RecyclerAddressAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemListAddressBinding) : RecyclerView.ViewHolder(binding.root) {

        fun initialize(viewHolder: ViewHolder , dataSet: AddressModel , action : OnClickAddressList){
            action.onClick( viewHolder , dataSet , adapterPosition)
        }

    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(ItemListAddressBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

        return myViewHolder
    }
    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.binding.tvAddressFullName.text   = dataSet[position].name
        viewHolder.binding.tvAddressType.text       = dataSet[position].type
        viewHolder.binding.tvAddressDetails.text    = "${dataSet[position].address} , ${dataSet[position].zipCode}"
        viewHolder.binding.tvAddressMobileNumber.text = dataSet[position].mobileNumber

        viewHolder.initialize( viewHolder , dataSet.get(position) , onClickAddressList)

        
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    // Notify for swipe edit
    fun notifyEditItem( fragmentAddressList : AddressListFragment , position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Constants.EXTRA_ADDRESS_DETAILS , dataSet[position])
        fragmentAddressList.findNavController().navigate(R.id.action_addressListFragment_to_addEditAddressFragment , bundle)
        notifyItemChanged(position)

    }

    interface OnClickAddressList{
        fun onClick(viewHolder: ViewHolder , dataSet : AddressModel , position: Int)
    }
}