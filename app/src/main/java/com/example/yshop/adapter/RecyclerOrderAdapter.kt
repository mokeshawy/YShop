package com.example.yshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.databinding.ItemListOrdersBinding
import com.example.yshop.model.CartItemModel


class RecyclerOrderAdapter (private val dataSet: ArrayList<CartItemModel>) : RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemListOrdersBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(ItemListOrdersBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

        return myViewHolder
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}