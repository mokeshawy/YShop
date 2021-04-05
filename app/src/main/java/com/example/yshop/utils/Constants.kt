package com.example.yshop.utils

import com.google.firebase.auth.FirebaseAuth

object Constants {

    // for "users" reference
    const val USERS : String = "users"
    // for product reference
    const val PRODUCT : String ="product"


    const val DATA_STORE_NAME   : String  = "UsersPreference"
    const val FIRST_NAME_KEY    : String  = "firstName"
    const val LAST_NAME_KEY     : String  = "lastName"
    const val USER_EMAIL_KEY    : String  = "userEmail"
    const val USER_IMAGE_KEY    : String  = "image"
    const val USER_MOBILE_KEY   : String  = "mobile"
    const val USER_GENDER_KEY   : String  = "gender"

    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE : String     = "Male"
    const val FEMALE : String   = "Female"

    const val MOBILE : String   = "mobile"
    const val GENDER : String   = "gender"

    const val PRODUCT_IMAGE : String = "product_image"

    const val USER_PROFILE_IMAGE : String = "user_profile_image"

    const val COMPLETE_PROFILE : String = "profileCompleted"

    const val USER_ID : String = "userId"
    const val PRODUCT_ID: String = "productId"

    // Product
    const val EXTRA_PRODUCT_ID : String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"

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