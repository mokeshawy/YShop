package com.example.yshop.feature.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.databinding.ItemListProductBinding
import com.example.yshop.feature.domain.model.OrderModel
import com.squareup.picasso.Picasso


class RecyclerOrderAdapter (private val dataSet: ArrayList<OrderModel>,
                            var onClick : OnClickOrder
) : RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder>() {

    class ViewHolder(var binding : ItemListProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun initialize(viewHolder: ViewHolder, dataSet: OrderModel, action : OnClickOrder){
            action.onClick( viewHolder , dataSet , adapterPosition)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(ItemListProductBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

        return myViewHolder
    }
    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvItemName.text  = dataSet[position].title
        viewHolder.binding.tvItemPrice.text = "$${dataSet[position].totalAmount}"
        Picasso.get().load(dataSet[position].image).into(viewHolder.binding.ivItemImage)

        // Invisible delete button
        viewHolder.binding.ibDeleteProduct.visibility = View.INVISIBLE

        viewHolder.initialize( viewHolder , dataSet[position], onClick)
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnClickOrder{
        fun onClick(viewHolder : ViewHolder, dataSet: OrderModel, position: Int)
    }
}