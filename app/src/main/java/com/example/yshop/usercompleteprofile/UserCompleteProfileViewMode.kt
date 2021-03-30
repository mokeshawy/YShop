package com.example.yshop.usercompleteprofile

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.yshop.R
import com.example.yshop.datastoreoperetion.DataStoreRepository
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserCompleteProfileViewMode : ViewModel() {

    var mobileNumber = MutableLiveData<String>("")


    // Show data for user from fireStore by save into dataStore
    fun showData(context: Context, etFirstNameName: EditText , etLastName : EditText , etUserEmail : EditText){

        viewModelScope.launch {
            etFirstNameName.setText(DataStoreRepository(context).showFirstName(Constants.FIRST_NAME_KEY))
            etLastName.setText(DataStoreRepository(context).showLastName(Constants.LAST_NAME_KEY))
            etUserEmail.setText(DataStoreRepository(context).showUserEmail(Constants.USER_EMAIL_KEY))
        }
    }

    // fun validate data entry mobileNumber
    fun validateInput(context: Context, view : View) : Boolean {
        return when {

            TextUtils.isEmpty(mobileNumber.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_mobile_number),true , context, view )
                false
            }
            else -> {
                true
            }
        }
    }

    var firebaseDatabase    = FirebaseDatabase.getInstance()
    var userReference       = firebaseDatabase.getReference(Constants.USERS)
    var myStorage           = FirebaseStorage.getInstance().reference
    fun completeProfile(context: Context, view: View, imageUri: Uri , radioButton : RadioButton ){

        if(validateInput(context , view)){
            // Show progressDialog
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)

            var refStorage : StorageReference = myStorage.child("Photo/"+Constants.USER_PROFILE_IMAGE+System.currentTimeMillis())
            refStorage.putFile(imageUri).addOnCompleteListener { saveImage ->
                if(saveImage.isSuccessful){
                    OptionBuilder.hideProgressDialog()
                    refStorage.downloadUrl.addOnSuccessListener { downloadImage ->

                        var map = HashMap<String , Any>()

                        // Check radioButton
                        var gender = if(radioButton.isChecked){
                            Constants.MALE
                        }else{
                            Constants.FEMALE
                        }

                        // Check mobileNumber is not empty
                        if(mobileNumber.value!!.isNotEmpty()){
                            map[Constants.MOBILE] = mobileNumber.value.toString()
                        }
                        map[Constants.GENDER]           = gender
                        map[Constants.USER_IMAGE_KEY]   = downloadImage.toString()
                        // After complete profile will change number 0 to 1 because when user needed log in entry to home page direct
                        map[Constants.COMPLETE_PROFILE] = 1

                        // update to data for real time data base
                        userReference.child(Constants.getCurrentUser()).updateChildren(map)


                    }
                    // Show snack bar for update successful
                    OptionBuilder.showErrorSnackBar(context.resources.getString(R.string.msg_profile_updated),false,context,view)
                    // going to home page =>
                    Navigation.findNavController(view).navigate(R.id.action_userCompleteProfileFragment_to_dashBoardFragment)
                }
            }
        }
    }
}