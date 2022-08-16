package com.example.yshop.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yshop.databinding.ItemListCartBinding
import com.example.yshop.feature.domain.model.CartItemModel
import com.squareup.picasso.Picasso

class RecyclerCartListAdapter(
    private var dataSet: ArrayList<CartItemModel>,
    var onClick: OnClickCartList,
    var updateCartItems: Boolean
) : RecyclerView.Adapter<RecyclerCartListAdapter.ViewHolder>() {


    class ViewHolder(var binding: ItemListCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun initialize(
            updateCartItems: Boolean,
            viewHolder: RecyclerView.ViewHolder,
            dataSet: CartItemModel,
            action: OnClickCartList
        ) {
            action.onClick(updateCartItems, viewHolder as ViewHolder, dataSet, adapterPosition)
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(
            ItemListCartBinding.inflate(
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
        viewHolder.binding.tvCartItemTitle.text = dataSet[position].title
        viewHolder.binding.tvCartItemPrice.text = "$ ${dataSet[position].price}"
        viewHolder.binding.tvCartQuantity.text = dataSet[position].cartQuantity
        Picasso.get().load(dataSet[position].image).into(viewHolder.binding.ivCartItemImage)

        viewHolder.initialize(updateCartItems, viewHolder, dataSet.get(position), onClick)


    }

    // Return the size of your dataSet (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    interface OnClickCartList {
        fun onClick(
            updateCartItems: Boolean,
            viewHolder: ViewHolder,
            dataSet: CartItemModel,
            position: Int
        )
    }
}