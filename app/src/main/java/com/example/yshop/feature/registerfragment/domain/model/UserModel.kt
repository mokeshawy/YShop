package com.example.yshop.feature.registerfragment.domain.model

data class UserModel (

    var id                  : String    = "",
    var firstName           : String    = "",
    var lastName            : String    = "",
    var email               : String    = "",
    var image               : String    = "",
    var mobile              : Int       = 0,
    var gender              : String    = "",
    var profileCompleted    : Int       = 0

)