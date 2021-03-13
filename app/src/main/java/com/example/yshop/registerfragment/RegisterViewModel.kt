package com.example.yshop.registerfragment
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yshop.optionbuilder.OptionBuilder
import com.example.yshop.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel : ViewModel() {

    var etFirstName         = MutableLiveData<String>("")
    var etLastName          = MutableLiveData<String>("")
    var etEmail             = MutableLiveData<String>("")
    var etPassword          = MutableLiveData<String>("")
    var etConfirmPassword   = MutableLiveData<String>("")


    // fun validate data entry from user register
    fun validateRegisterDetails(context: Context , view : View , checkBox: CheckBox) : Boolean {

        return when {
            TextUtils.isEmpty(etFirstName.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_first_name),true , context, view )
                false
            }

            TextUtils.isEmpty(etLastName.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_last_name),true , context, view )
                false
            }

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
            TextUtils.isEmpty(etConfirmPassword.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_confirm_Password),true , context, view )
                false
            }

            etPassword.value.toString().trim { it <=' ' } != etConfirmPassword.value.toString().trim { it <=' ' } -> {
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_password_and_confirm_Password_mismatch), true , context, view )
                false
            }

            !checkBox.isChecked -> {
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_terms_and_condition), true , context, view )
                false
            }
            else -> {
//              showErrorSnackBar(context.getString(R.string.registery_successful),false , context, view )
                true
            }
        }
    }

    var firebaseAuth = FirebaseAuth.getInstance()
    // fun register user
    fun registerUser(context: Context , view : View , checkBox: CheckBox){

        // check validate function if the entries are valid or no
        if(validateRegisterDetails(context , view , checkBox)){
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait) , context)
            var email       = etEmail.value.toString().trim { it <= ' ' }
            var password    = etPassword.value.toString().trim { it <= ' ' }

            firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(
                OnCompleteListener<AuthResult>{task ->
                    if(task.isSuccessful){

                        OptionBuilder.hideProgressDialog()

                        firebaseAuth.currentUser?.sendEmailVerification()

                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        OptionBuilder.showErrorSnackBar("You are registered successfully. Your user id is ${firebaseUser.uid} ",false , context , view)
                        true
                    }else{
                        OptionBuilder.showErrorSnackBar(task.exception!!.message.toString(), true , context, view)
                        false
                    }
                }
            )
        }
    }
}