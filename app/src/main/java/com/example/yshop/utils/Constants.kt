package com.example.yshop.utils

import com.google.firebase.auth.FirebaseAuth

object Constants {

    // for "users" reference
    const val USERS : String = "users"

    // Child for user Reference and key for dataStore
    const val DATA_STORE_NAME   : String  = "UsersPreference"
    const val FIRST_NAME_KEY    : String  = "firstName"
    const val LAST_NAME_KEY     : String  = "lastName"
    const val USER_EMAIL_KEY    : String  = "email"
    const val USER_IMAGE_KEY    : String  = "image"
    const val USER_MOBILE_KEY   : String  = "mobile"
    const val USER_GENDER_KEY   : String  = "gender"
    const val USER_COMPLETE_PROFILE : String = "profileCompleted"

    // User complete profile image data store
    const val USER_PROFILE_IMAGE : String = "user_profile_image"

    // male or female string
    const val MALE : String     = "Male"
    const val FEMALE : String   = "Female"

    // Request code for pick image from storage
    const val PICK_IMAGE_REQUEST_CODE = 1

    // Product image file
    const val PRODUCT_IMAGE_FILE : String = "product_image"

    // for product reference
    const val PRODUCT : String ="product"
    // Product Child
    const val PRODUCT_USER_ID : String = "userId"
    const val PRODUCT_USER_NAME : String = "userName"
    const val PRODUCT_TITLE :String = "title"
    const val PRODUCT_PRICE : String = "price"
    const val PRODUCT_DESC : String = "description"
    const val PRODUCT_QUANTITY : String = "stockQuantity"
    const val PRODUCT_IMAGE : String = "productImage"

    // Product extra
    const val EXTRA_PRODUCT_ID : String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"



    //CartItem Reference
    const val CART_ITEM : String = "cartItem"
    // CartItem
    const val CART_QUANTITY: String = "cartQuantity"
    const val DEFAULT_CART_ITEM : String = "1"
    const val PRODUCT_ID    : String = "productId"
    const val USER_ID       : String = "userId"

    // Add address Reference
    const val ADD_ADDRESS : String = "address"

    const val HOME: String = "Home"
    const val OFFICE: String = "Office"
    const val OTHER: String = "Other"

    const val EXTRA_ADDRESS_DETAILS : String = "AddressDetails"


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