package com.example.yshop.model

data class UserModel (

    var id                  : String    = "",
    var firstName           : String    = "",
    var lastName            : String    = "",
    var email               : String    = "",
    var image               : String    = "",
    var mobile              : Long      = 0,
    var gender              : String    = "",
    var profileCompleted    : Int       = 0

)