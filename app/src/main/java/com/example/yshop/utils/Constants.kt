package com.example.yshop.utils

import com.google.firebase.auth.FirebaseAuth

object Constants {

    // for "users" collection
    const val USERS : String = "users"
    const val DATA_STORE_NAME : String = "UsersPreference"
    const val FIRST_NAME_KEY : String = "firstName"
    const val LAST_NAME_KEY : String  = "lastName"
    const val USER_EMAIL_KEY : String = "userEmail"
    const val USER_IMAGE_KEY : String = "image"
    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE : String = "Male"
    const val FEMALE : String = "Female"

    const val MOBILE : String = "mobile"
    const val GENDER : String = "gender"

    const val USER_PROFILE_IMAGE = "user_profile_image"


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