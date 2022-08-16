package com.example.yshop.feature.addeditaddressfragment.domain.viewmodel

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.yshop.R
import com.example.yshop.core.utils.Constants
import com.example.yshop.core.utils.OptionBuilder
import com.example.yshop.feature.addeditaddressfragment.presentation.AddEditAddressFragment
import com.example.yshop.feature.addresslistfragment.data.model.request.AddressModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class AddEditAddressViewModel : ViewModel() {

    var mAddressDetails = MutableLiveData<AddressModel>()
    var etFullName = MutableLiveData<String>("")
    var etPhoneNumber = MutableLiveData<String>("")
    var etAddress = MutableLiveData<String>("")
    var etZipCode = MutableLiveData<String>("")
    var etAdditionalNote = MutableLiveData<String>("")
    var etOtherDetails = MutableLiveData<String>("")


    // Validate input for add address
    private fun validate(context: Context, view: View, rb_other: RadioButton): Boolean {

        return when {
            TextUtils.isEmpty(etFullName.value!!.trim { it <= ' ' }) -> {
                OptionBuilder.showErrorSnackBar(
                    context.getString(R.string.err_msg_please_enter_full_name),
                    true,
                    context,
                    view
                )
                false
            }
            TextUtils.isEmpty(etPhoneNumber.value!!.trim { it <= ' ' }) -> {
                OptionBuilder.showErrorSnackBar(
                    context.resources.getString(R.string.err_msg_please_enter_phone_number),
                    true,
                    context,
                    view
                )
                false
            }
            TextUtils.isEmpty(etAddress.value!!.trim { it <= ' ' }) -> {
                OptionBuilder.showErrorSnackBar(
                    context.resources.getString(R.string.err_msg_please_enter_address),
                    true,
                    context,
                    view
                )
                false
            }
            TextUtils.isEmpty(etZipCode.value!!.trim { it <= ' ' }) -> {
                OptionBuilder.showErrorSnackBar(
                    context.resources.getString(R.string.err_msg_please_enter_zip_code),
                    true,
                    context,
                    view
                )
                false
            }
            rb_other.isChecked && TextUtils.isEmpty(etZipCode.value!!.trim { it <= ' ' }) -> {
                OptionBuilder.showErrorSnackBar(
                    context.resources.getString(R.string.err_msg_please_enter_zip_code),
                    true,
                    context,
                    view
                )
                false
            }

            else -> {
                true
            }

        }
    }

    var firebaseDatabase = FirebaseDatabase.getInstance()
    var addressReference = firebaseDatabase.getReference(Constants.ADD_ADDRESS_REF)

    // save information for address
    fun saveAddress(
        addEditAddressFragment: AddEditAddressFragment,
        context: Context,
        view: View,
        rb_home: RadioButton,
        rb_office: RadioButton,
        rb_other: RadioButton
    ) {

        var addressDetails =
            addEditAddressFragment.arguments?.getSerializable(Constants.EXTRA_ADDRESS_DETAILS) as AddressModel?
        var fullName = etFullName.value!!.trim { it <= ' ' }
        var phoneNumber = etPhoneNumber.value!!.trim { it <= ' ' }
        var address = etAddress.value!!.trim { it <= ' ' }
        var zipCode = etZipCode.value!!.trim { it <= ' ' }
        var additionalNote = etAdditionalNote.value!!.trim { it <= ' ' }
        var otherDetails = etOtherDetails.value!!.trim { it <= ' ' }

        if (validate(context, view, rb_other)) {


            var addressType = when {

                rb_home.isChecked -> {
                    Constants.ADDRESS_HOME
                }
                rb_office.isChecked -> {
                    Constants.ADDRESS_OFFICE
                }
                else -> {
                    Constants.ADDRESS_OTHER
                }
            }

            val addressModel = AddressModel(
                Constants.getCurrentUser(),
                fullName,
                phoneNumber,
                address,
                zipCode,
                additionalNote,
                addressType,
                otherDetails
            )

            Navigation.findNavController(view)
                .navigate(R.id.action_addEditAddressFragment_to_addressListFragment)
            if (addressDetails != null && addressDetails!!.id.isNotEmpty()) {

                var map = HashMap<String, Any>()
                map[Constants.ADDRESS_NAME] = fullName
                map[Constants.ADDRESS_MOBILE_NUMBER] = phoneNumber
                map[Constants.ADDRESS] = address
                map[Constants.ADDRESS_ZIP_CODE] = zipCode
                map[Constants.ADDRESS_ADDITIONAL_NOTE] = additionalNote
                map[Constants.ADDRESS_TYPE] = addressType
                map[Constants.ADDRESS_OTHER_DETAILS] = otherDetails

                addressReference.child(addressDetails!!.id).updateChildren(map)

            } else {
                addressReference.push().setValue(addressModel)
            }
        }
    }

    // Button for check other
    fun checkedOther(til_other_details: TextInputLayout, rg_type: RadioGroup) {
        rg_type.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_other) {
                til_other_details.visibility = View.VISIBLE
            } else {
                til_other_details.visibility = View.GONE
            }
        }
    }
}