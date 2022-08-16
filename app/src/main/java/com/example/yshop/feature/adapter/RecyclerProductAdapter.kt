package com.example.yshop.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.databinding.ItemListProductBinding
import com.example.yshop.feature.dashboardfragment.data.model.request.ProductModel
import com.squareup.picasso.Picasso

class RecyclerProductAdapter(
    private val dataSet: ArrayList<ProductModel>,
    var onClick: OnClickProduct
) : RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemListProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun initialize(
            viewHolder: RecyclerView.ViewHolder,
            dataSet: ProductModel,
            action: OnClickProduct
        ) {
            action.onClick(viewHolder as ViewHolder, dataSet, adapterPosition)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(
            ItemListProductBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )

        return myViewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.binding.tvItemName.text = dataSet[position].title
        viewHolder.binding.tvItemPrice.text = "$ ${dataSet[position].price}"
        Picasso.get().load(dataSet[position].productImage).into(viewHolder.binding.ivItemImage)

        viewHolder.initialize(viewHolder, dataSet.get(position), onClick)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnClickProduct {
        fun onClick(viewHolder: ViewHolder, dataSet: ProductModel, position: Int)
    }
}