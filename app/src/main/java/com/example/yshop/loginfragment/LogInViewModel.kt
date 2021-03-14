package com.example.yshop.loginfragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.yshop.optionbuilder.OptionBuilder
import com.example.yshop.R
import com.google.firebase.auth.FirebaseAuth

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
                //OptionBuilder.showErrorSnackBar(context.getString(R.string.login_successful),false , context, view )
                true
            }
        }
    }

    // Connect whit authentication firebase
    var firebaseAuth = FirebaseAuth.getInstance()
    // Fun logIn
    fun userLogIn(context: Context , view: View){

        // check validate function if the entries are valid or no
        if(validateRegisterDetails(context , view)){
            // Show the progressDialog
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)

            // Get the text from editText and trim space
            var email       = etEmail.value.toString().trim { it <=' ' }
            var password    = etPassword.value.toString().trim { it <=' ' }

            // Log-In using firebase authentication
            firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener {
                if(it.isSuccessful){
                    if(firebaseAuth.currentUser?.isEmailVerified!!){

                        // Hide the progressDialog
                        OptionBuilder.hideProgressDialog()

                        // Show snackBar for success message
                        OptionBuilder.showErrorSnackBar(context.getString(R.string.login_successful),false , context, view )

                    }else{

                        // Hide the progressDialog
                        OptionBuilder.hideProgressDialog()

                        // if user not emailVerified   show error message
                        OptionBuilder.showErrorSnackBar(context.getString(R.string.err_email_not_confirm) , true , context, view )

                    }
                }else{

                    // Hide the progressDialog
                    OptionBuilder.hideProgressDialog()

                    // if operation for reset password not successful show error message
                    OptionBuilder.showErrorSnackBar(it.exception!!.message.toString(), true , context, view)

                }
            }
        }
    }


    // go forget password page
    fun goForgetPassPage(view: View){
        Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_forgetPasswordFragment)
    }

    // go to register new user page
    fun goRegisterPage(view: View){
        Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_registerFragment)
    }
}