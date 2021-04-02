package com.example.yshop.productsfargment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerProductAdapter
import com.example.yshop.databinding.FragmentProductsBinding
import com.example.yshop.model.ProductModel


class ProductsFragment : Fragment() {

    lateinit var binding    : FragmentProductsBinding
    val productsViewModel   : ProductsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Connect whit viewModel
        binding.lifecycleOwner  = this
        binding.productsVarModel    = productsViewModel

        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)

        // Call function for operation get product details from firebase and view im recycler view
        productsViewModel.getProductDetails( binding.rvMyProductItems , binding.tvNoProductsFound )
        productsViewModel.getProductDetails.observe(viewLifecycleOwner, Observer {
            binding.rvMyProductItems.adapter = RecyclerProductAdapter(it)
        })


    }

    // Create the icon menu in the action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_product_menu,menu)
    }

    // SelectItem for menu we want create the page for it
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_add_product ->{
                findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}