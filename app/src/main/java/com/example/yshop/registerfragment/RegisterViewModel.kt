package com.example.yshop.registerfragment
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yshop.R
import com.google.android.material.snackbar.Snackbar

class RegisterViewModel : ViewModel() {

    var etFirstName         = MutableLiveData<String>("")
    var etLastName          = MutableLiveData<String>("")
    var etEmail             = MutableLiveData<String>("")
    var etPassword          = MutableLiveData<String>("")
    var etConfirmPassword   = MutableLiveData<String>("")
    var cbTermsAndCondition = MutableLiveData<Boolean>()


    // fun snack bar show error and successful
    fun showErrorSnackBar( message : String , errorMessage : Boolean , context: Context , view : View){

        val snackBar = Snackbar.make( view , message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor( context , R.color.colorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(context , R.color.colorSnackBarSuccess))
        }
        snackBar.show()
    }

    // fun validate data entry from user register
    fun validateRegisterDetails(context: Context , view : View , checkBox: CheckBox) : Boolean {

        return when {
            TextUtils.isEmpty(etFirstName.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_first_name),true , context, view )
                false
            }

            TextUtils.isEmpty(etLastName.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_last_name),true , context, view )
                false
            }

            TextUtils.isEmpty(etEmail.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_email),true , context, view )
                false
            }

            TextUtils.isEmpty(etPassword.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_Password),true , context, view )
                false
            }

            TextUtils.isEmpty(etConfirmPassword.value.toString().trim { it <=' ' }) ->{
                showErrorSnackBar(context.getString(R.string.err_msg_enter_confirm_Password),true , context, view )
                false
            }

            etPassword.value.toString().trim { it <=' ' } != etConfirmPassword.value.toString().trim { it <=' ' } -> {
                showErrorSnackBar(context.getString(R.string.err_msg_password_and_confirm_Password_mismatch), true , context, view )
                false
            }

            !checkBox.isChecked -> {
                showErrorSnackBar(context.getString(R.string.err_msg_terms_and_condition), true , context, view )
                false
            }
            else -> {
                showErrorSnackBar(context.getString(R.string.registery_successful),false , context, view )
                true
            }
        }
    }
}