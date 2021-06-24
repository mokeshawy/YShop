package com.example.yshop.addresslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.adapter.RecyclerAddressAdapter
import com.example.yshop.databinding.FragmentAddressListBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants

class AddressListFragment : Fragment() , RecyclerAddressAdapter.OnClickAddressList {

    lateinit var binding                : FragmentAddressListBinding
    private val addressListViewModel    : AddressListViewModel by viewModels()
    private var mSelectedAddress        : Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddressListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner      = this
        binding.addressListVarModel = addressListViewModel

        binding.toolbarAddressListFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
        // When entry from setting will back setting again
        binding.toolbarAddressListFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addressListFragment_to_settingsFragment)
        }

        // Check argument pass from cart item fragment
        if( arguments?.containsKey(Constants.EXTRA_ADDRESS_DETAILS) == true){
            mSelectedAddress = arguments?.getBoolean(Constants.EXTRA_ADDRESS_DETAILS , false) as Boolean
        }

        // Check entry from add cart list to address list
        if(mSelectedAddress){
            // When entry from add cart will back to cart list again
            binding.toolbarAddressListFragment.setNavigationOnClickListener {
                findNavController().navigate(R.id.action_addressListFragment_to_cartListFragment)
            }

            // Button for go add new address GONE when user entry select address from add cart fragment
            binding.tvAddAddress.visibility = View.GONE
            binding.tvTitle.text = resources.getString(R.string.title_select_address)

        }else{

            // Button for go add new address entry from setting page
            binding.tvAddAddress.setOnClickListener {
                findNavController().navigate(R.id.action_addressListFragment_to_addEditAddressFragment)
            }

        }

        // Show list address from firebase database
        addressListViewModel.getAddressList( requireActivity() , binding.rvAddressList , binding.tvNoAddressFound , this )
        addressListViewModel.addressList.observe(viewLifecycleOwner , Observer {
            binding.rvAddressList.adapter = RecyclerAddressAdapter(it , this)

        })


    }

    
    override fun onClick(viewHolder: RecyclerAddressAdapter.ViewHolder, dataSet: AddressModel, position: Int) {
        viewHolder.itemView.setOnClickListener {
            if(mSelectedAddress){
                var bundle = Bundle()
                bundle.putSerializable(Constants.EXTRA_SELECTED_ADDRESS , dataSet)
                findNavController().navigate(R.id.action_addressListFragment_to_checkOutFragment , bundle)
            }
        }
    }
}