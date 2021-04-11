package com.example.yshop.addeditaddressfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentAddEditAddressBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

class AddEditAddressFragment : Fragment() {


    lateinit var binding                : FragmentAddEditAddressBinding
    private val addEditAddressViewModel : AddEditAddressViewModel by viewModels()
     var mAddressDetails         : AddressModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddEditAddressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.addEditAddressVarModel = addEditAddressViewModel

        // Check for received  object pass whit account log in
        if( arguments?.containsKey(Constants.EXTRA_ADDRESS_DETAILS) == true){
           mAddressDetails =  arguments?.getSerializable(Constants.EXTRA_ADDRESS_DETAILS) as AddressModel?
        }

        // Check object pass not equal null
        if( mAddressDetails != null){
            if( mAddressDetails!!.id.isNotEmpty()){

                binding.tvTitle.text            = resources.getString(R.string.title_edit_address)
                binding.btnSubmitAddress.text   = resources.getString(R.string.btn_lbl_update)

                binding.etFullName.setText(mAddressDetails!!.name)
            }
        }


        binding.btnSubmitAddress.setOnClickListener {
            OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
            addEditAddressViewModel.saveAddress( requireActivity() , view ,  binding.rbHome , binding.rbOffice , binding.rbOther)
            OptionBuilder.hideProgressDialog()
        }

        addEditAddressViewModel.checkedOther( binding.tilOtherDetails , binding.rgType)
    }
}