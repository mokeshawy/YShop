package com.example.yshop.model

import java.io.Serializable

data class ProductModel (

      var userId        : String = "",
      var userName      : String ="",
      var title         : String = "",
      var price         : String = "",
      var description   : String = "",
      var stockQuantity : String = "",
      var productImage  : String = "",
      var productId     : String = ""

) : Serializable