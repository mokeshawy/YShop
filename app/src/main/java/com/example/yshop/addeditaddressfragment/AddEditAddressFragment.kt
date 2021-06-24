package com.example.yshop.addeditaddressfragment

import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentAddEditAddressBinding
import com.example.yshop.model.AddressModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

class AddEditAddressFragment : Fragment() {


    lateinit var binding                : FragmentAddEditAddressBinding
    private val addEditAddressViewModel : AddEditAddressViewModel by viewModels()
    var mAddressDetails                 : AddressModel? = null

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

        // Check for received  object by bundle pass whit account log in
        if( arguments?.containsKey(Constants.EXTRA_ADDRESS_DETAILS) == true){
           mAddressDetails =  arguments?.getSerializable(Constants.EXTRA_ADDRESS_DETAILS) as AddressModel?
        }

        // Check object pass not equal null
        if( mAddressDetails != null){
            if( mAddressDetails!!.id.isNotEmpty()){
                // when swipe user on address item will entry to edit address page and change title for toolbar and button
                binding.tvTitle.text            = resources.getString(R.string.title_edit_address)
                binding.btnSubmitAddress.text   = resources.getString(R.string.btn_lbl_update)

                // Show data from object pass by bundle
                addEditAddressViewModel.etFullName.value        = mAddressDetails!!.name
                addEditAddressViewModel.etPhoneNumber.value     = mAddressDetails!!.mobileNumber
                addEditAddressViewModel.etAddress.value         = mAddressDetails!!.address
                addEditAddressViewModel.etZipCode.value         = mAddressDetails!!.zipCode
                addEditAddressViewModel.etAdditionalNote.value  = mAddressDetails!!.additionalNote

                when(mAddressDetails!!.type){
                    Constants.ADDRESS_HOME ->{
                        binding.rbHome.isChecked = true
                    }
                    Constants.ADDRESS_OFFICE ->{
                        binding.rbOffice.isChecked = true
                    }
                    Constants.ADDRESS_OTHER ->{
                        binding.rbOther.isChecked = true
                        binding.tilOtherDetails.visibility              = View.VISIBLE
                        addEditAddressViewModel.etOtherDetails.value    = mAddressDetails!!.otherDetails
                    }
                }

            }
        }

        // Button add address
        binding.btnSubmitAddress.setOnClickListener {
            OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
            addEditAddressViewModel.saveAddress( this ,requireActivity() , view ,  binding.rbHome , binding.rbOffice , binding.rbOther)

            // Check when user entry from add new address or edit address after check will show toast update or new add
           val notifySuccessMessage  =  if( mAddressDetails != null && mAddressDetails!!.id.isNotEmpty()){
                     resources.getString(R.string.msg_your_address_updated_successfully)
            }else{
                    resources.getString(R.string.msg_your_address_added_successfully)
           }
            Toast.makeText(requireActivity() , notifySuccessMessage , Toast.LENGTH_SHORT).show()
            OptionBuilder.hideProgressDialog()
        }

        // Show otherText when click user on other check button
        addEditAddressViewModel.checkedOther( binding.tilOtherDetails , binding.rgType)
    }
}