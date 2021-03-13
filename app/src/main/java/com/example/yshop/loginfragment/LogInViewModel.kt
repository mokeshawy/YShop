package com.example.yshop.loginfragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yshop.R
import com.google.android.material.snackbar.Snackbar

class LogInViewModel : ViewModel() {

    var etEmail     = MutableLiveData<String>("")
    var etPassword  = MutableLiveData<String>("")




    // fun snack bar show error and successful
    fun showErrorSnackBar(message : String, errorMessage : Boolean, context: Context, view : View){

        val snackBar = Snackbar.make( view , message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor( context , R.color.colorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(context , R.color.colorSnackBarSuccess))
        }
        snackBar.show()
    }


    // fun validate data entry from user login
    fun validateRegisterDetails(context: Context , view : View ) : Boolean {

        return when {

            TextUtils.isEmpty(etEmail.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_email),true , context, view )
                false
            }

            TextUtils.isEmpty(etPassword.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_Password),true , context, view )
                false
            }
            etPassword.value.toString().length < 6 ->{

                showErrorSnackBar(context.getString(R.string.err_msg_length_Password),true , context, view )
                false
            }
            else -> {
                showErrorSnackBar(context.getString(R.string.login_successful),false , context, view )
                true
            }
        }
    }
}