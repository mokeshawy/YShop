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
    const val PRODUCT_USER_ID       : String = "userId"
    const val PRODUCT_USER_NAME     : String = "userName"
    const val PRODUCT_TITLE         : String = "title"
    const val PRODUCT_PRICE         : String = "price"
    const val PRODUCT_DESC          : String = "description"
    const val PRODUCT_QUANTITY      : String = "stockQuantity"
    const val PRODUCT_IMAGE         : String = "productImage"

    // Product extra
    const val EXTRA_PRODUCT_ID          : String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID    : String = "extra_product_owner_id"



    //CartItem Reference
    const val CART_ITEM         : String = "cartItem"
    // CartItem
    const val CART_QUANTITY     : String = "cartQuantity"
    const val DEFAULT_CART_ITEM : String = "1"
    const val PRODUCT_ID        : String = "productId"
    const val USER_ID           : String = "userId"

    // Add address Reference
    const val ADD_ADDRESS_REF : String = "address"

    // Address child
    const val ADDRESS_HOME              : String = "Home"
    const val ADDRESS_OFFICE            : String = "Office"
    const val ADDRESS_OTHER             : String = "Other"
    const val ADDRESS_NAME              : String = "name"
    const val ADDRESS_MOBILE_NUMBER     : String = "mobileNumber"
    const val ADDRESS                   : String = "address"
    const val ADDRESS_ZIP_CODE          : String = "zipCode"
    const val ADDRESS_ADDITIONAL_NOTE   : String = "additionalNote"
    const val ADDRESS_TYPE              : String = "type"
    const val ADDRESS_OTHER_DETAILS     : String = "otherDetails"

    const val EXTRA_ADDRESS_DETAILS     : String = "AddressDetails"
    const val EXTRA_SELECT_ADDRESS      : String = "extra_select_address"
    const val ADD_ADDRESS_REQUEST       : String    = "request"
    const val EXTRA_SELECTED_ADDRESS    : String = "extra_selected_address"

    // Orders Reference
    const val ORDER_REF = "order"

    const val EXTRA_MY_ORDER_DETAILS: String = "extra_my_order_details"

    const val EXTRA_SOLD_PRODUCT_DETAILS: String = "extra_sold_product_details"


    // Sold Product Reference
   const val SOLD_PRODUCT_REF : String = "soldProduct"


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