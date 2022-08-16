package com.example.yshop.feature.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.databinding.ItemListProductBinding
import com.example.yshop.feature.domain.model.SoldProductModel
import com.squareup.picasso.Picasso

class RecyclerSoldProductAdapter(
    private val dataSet: ArrayList<SoldProductModel>,
    var onClick: OnClickSoldProduct
) : RecyclerView.Adapter<RecyclerSoldProductAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemListProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun initialize(
            viewHolder: ViewHolder,
            dataSet: SoldProductModel,
            action: OnClickSoldProduct
        ) {
            action.onClick(viewHolder, dataSet, adapterPosition)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ViewHolder(
            ItemListProductBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvItemName.text = dataSet[position].title
        viewHolder.binding.tvItemPrice.text = "$${dataSet[position].price}"
        Picasso.get().load(dataSet[position].image).into(viewHolder.binding.ivItemImage)

        viewHolder.binding.ibDeleteProduct.visibility = View.INVISIBLE

        // Invisible delete button
        viewHolder.binding.ibDeleteProduct.visibility = View.INVISIBLE

        viewHolder.initialize(viewHolder, dataSet[position], onClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnClickSoldProduct {
        fun onClick(viewHolder: ViewHolder, dataSet: SoldProductModel, position: Int)
    }
}