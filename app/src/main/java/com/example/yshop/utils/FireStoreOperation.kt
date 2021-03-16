package com.example.yshop.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yshop.R
import com.example.yshop.loginfragment.LogInFragment
import com.example.yshop.loginfragment.LogInViewModel
import com.example.yshop.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlin.math.log

object FireStoreOperation {

    // Connect whit fireStore
    private val mFireStore = FirebaseFirestore.getInstance()
    // Register function
    fun registerUser(context : Context ,  userInfo : UserModel){

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge()).addOnCompleteListener {
                if(it.isSuccessful){

                    // hide the progressDialog
                    OptionBuilder.hideProgressDialog()
                    // Show toast for success register
                    Toast.makeText(context , context.resources.getText(R.string.registery_successful), Toast.LENGTH_SHORT).show()

                }else{
                    // show message for error where operation for not successful
                    Toast.makeText(context , it.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener { e ->
            // hide the progressDialog
            OptionBuilder.hideProgressDialog()
            // Toast for show error where problem for register
            Toast.makeText(context , "Error while registration the user ", Toast.LENGTH_SHORT).show()
        }
    }

    // getUserID function
    fun getCurrentUser() : String{
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is nut null or else it will be blank
        var currentUserID = ""
        if(currentUserID != null){

            currentUserID = currentUser.uid
        }
        return currentUserID
    }

}