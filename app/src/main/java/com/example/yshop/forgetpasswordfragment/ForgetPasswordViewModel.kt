package com.example.yshop.forgetpasswordfragment

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.example.yshop.R
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ForgetPasswordViewModel : ViewModel() {

    // Connect whit dataStore
    lateinit var dataStore : DataStore<Preferences>

    var etEmail = MutableLiveData<String>("")

    // fun validate data entry from user login
    fun validateInput(context: Context, view : View) : Boolean {
        return when {

            TextUtils.isEmpty(etEmail.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_email),true , context, view )
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
    // fun create operation for forgetPassword
    fun forgetPassword(context: Context , view: View){
        // check validate function if the entries are valid or no
        if(validateInput(context , view )){

            // Show progressDialog
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)

            // Get the text from editText and trim space
            var email = etEmail.value.toString().trim { it <=' ' }

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if(it.isSuccessful){

                    // Hide progressDialog
                    OptionBuilder.hideProgressDialog()

                    // Show the snackBar for success send email for reset password
                    OptionBuilder.showErrorSnackBar(context.resources.getString(R.string.email_sent_success),false , context, view)

                    // Go to logIn page after send email for reset password
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_logInFragment)

                }else{
                    // Hide progressDialog
                    OptionBuilder.hideProgressDialog()

                    // if operation for reset password not successful show error message
                    OptionBuilder.showErrorSnackBar(it.exception!!.message.toString(),true , context, view)
                }
            }

        }
    }

    fun showData( context: Context , textView: TextView){

         dataStore = context.createDataStore(Constants.DATA_STORE_NAME)

        viewModelScope.launch {
            textView.text = showFirstName(Constants.FIRST_NAME_KEY)
        }
    }

    suspend fun showFirstName( key : String ) : String?{
        var dataStoreKey = preferencesKey<String>(key)
        var preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun showLastName( key : String ) : String?{
        var dataStoreKey = preferencesKey<String>(key)
        var preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun showUserEmail( key : String ) : String?{
        var dataStoreKey = preferencesKey<String>(key)
        var preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}