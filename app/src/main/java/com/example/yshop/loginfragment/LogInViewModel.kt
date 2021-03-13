package com.example.yshop.loginfragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yshop.optionbuilder.OptionBuilder
import com.example.yshop.R

class LogInViewModel : ViewModel() {

    var etEmail     = MutableLiveData<String>("")
    var etPassword  = MutableLiveData<String>("")


    // fun validate data entry from user login
    fun validateRegisterDetails(context: Context , view : View ) : Boolean {

        return when {

            TextUtils.isEmpty(etEmail.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_email),true , context, view )
                false
            }

            TextUtils.isEmpty(etPassword.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_Password),true , context, view )
                false
            }
            etPassword.value.toString().length < 6 ->{

                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_length_Password),true , context, view )
                false
            }
            else -> {
                OptionBuilder.showErrorSnackBar(context.getString(R.string.login_successful),false , context, view )
                true
            }
        }
    }
}