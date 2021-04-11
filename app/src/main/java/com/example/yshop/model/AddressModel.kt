package com.example.yshop.model

import java.io.Serializable

data class AddressModel (

        var userId          : String = "",
        var name            : String = "",
        var mobileNumber    : String = "",
        var address         : String = "",
        var zipCode         : String = "",
        var additionalNote  : String = "",
        var type            : String = "",
        var otherDetails    : String = "",
        var id              : String = ""

) : Serializable