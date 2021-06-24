package com.example.yshop.model

import java.io.Serializable

data class CartItemModel(

        var userId          : String = "",
        var productId       : String = "",
        var title           : String = "",
        var price           : String = "",
        var image           : String = "",
        var cartQuantity    : String = "",
        var stockQuantity   : String = "",
        var id              : String = ""

) : Serializable