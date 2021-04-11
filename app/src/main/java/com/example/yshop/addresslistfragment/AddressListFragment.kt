package com.example.yshop.addresslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerAddressAdapter
import com.example.yshop.databinding.FragmentAddressListBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants

class AddressListFragment : Fragment() , RecyclerAddressAdapter.OnClickAddressList {

    lateinit var binding        : FragmentAddressListBinding
    val addressListViewModel    : AddressListViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner      = this
        binding.addressListVarModel = addressListViewModel

        binding.toolbarAddressListFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarAddressListFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addressListFragment_to_settingsFragment)
        }

        addressListViewModel.getAddressList( requireActivity() , binding.rvAddressList , binding.tvNoAddressFound , this )
        addressListViewModel.addressList.observe(viewLifecycleOwner , Observer {
            binding.rvAddressList.adapter = RecyclerAddressAdapter(it , this)

        })

        binding.tvAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressListFragment_to_addEditAddressFragment)
        }

    }

    override fun onClick(viewHolder: RecyclerAddressAdapter.ViewHolder, dataSet: AddressModel, position: Int) {

    }
}