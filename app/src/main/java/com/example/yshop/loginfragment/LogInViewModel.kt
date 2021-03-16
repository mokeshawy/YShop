package com.example.yshop.loginfragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.yshop.utils.OptionBuilder
import com.example.yshop.R
import com.example.yshop.model.UserModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.FireStoreOperation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {

    // Connect whit dataStore
    lateinit var dataStore  : DataStore<Preferences>
    // get object form UserModel
    lateinit var userModel: UserModel



    var etEmail     = MutableLiveData<String>("")
    var etPassword  = MutableLiveData<String>("")
    //var userModel = MutableLiveData<UserModel>()

    // fun validate data entry from user login
    fun validateInput(context: Context , view : View ) : Boolean {

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
    // Connect whit fireStore
     val mFireStore = FirebaseFirestore.getInstance()
    // Fun logIn
    fun userLogIn(context: Context , view: View ){
        // init userModel
        userModel = UserModel()
        // check validate function if the entries are valid or no
        if(validateInput(context , view)){
            // Show the progressDialog
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)

            // Get the text from editText and trim space
            var email       = etEmail.value.toString().trim { it <=' ' }
            var password    = etPassword.value.toString().trim { it <=' ' }

            // Log-In using firebase authentication
            firebaseAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener {
                if(it.isSuccessful){
                    //if(firebaseAuth.currentUser?.isEmailVerified!!){

                        // Hide the progressDialog
                        OptionBuilder.hideProgressDialog()

                        // Show snackBar for success message
                        OptionBuilder.showErrorSnackBar(context.getString(R.string.login_successful),false , context, view )

                    mFireStore.collection(Constants.USERS).document(FireStoreOperation.getCurrentUser()).get().addOnSuccessListener { result ->
                        var firstName   = result["firstName"].toString()
                        var lastName    = result["lastName"].toString()
                        var userEmail       = result["email"].toString()


                        dataStore = context.createDataStore(Constants.DATA_STORE_NAME)
                        viewModelScope.launch {
                            saveDataStoreDetails(firstName, lastName, userEmail)
                        }

                    }

                        if(userModel.profileCompleted == 0){
                            // If the user profile is incomplete then launch the userCompleteProfileFragment
                            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_userCompleteProfileFragment)
                        }else{
                            // Redirect to home page

                        }

//                    }else{
//
//                        // Hide the progressDialog
//                        OptionBuilder.hideProgressDialog()
//
//                        // if user not emailVerified   show error message
//                        OptionBuilder.showErrorSnackBar(context.getString(R.string.err_email_not_confirm) , true , context, view )
//
//                    }

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

    suspend fun saveDataStoreDetails( firstName : String , lastName : String , userEmail : String){
        dataStore.edit { dataPref ->
            dataPref[preferencesKey<String>(Constants.FIRST_NAME_KEY)]  = firstName
            dataPref[preferencesKey<String>(Constants.LAST_NAME_KEY)]   = lastName
            dataPref[preferencesKey<String>(Constants.USER_EMAIL_KEY)]  = userEmail
        }
    }
}