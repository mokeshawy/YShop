package com.example.yshop.feature.domain.model

import com.example.yshop.feature.addresslistfragment.data.model.request.AddressModel
import com.example.yshop.feature.domain.model.CartItemModel
import java.io.Serializable

data class OrderModel (

    var userId              : String = "",
    var items               : ArrayList<CartItemModel> = ArrayList(),
    var address             : AddressModel = AddressModel(),
    var title               : String = "",
    var image               : String = "",
    var subTotalAmount      : String = "",
    var shippingCharge      : String = "",
    var totalAmount         : String = "",
    var orderDataTime       : Long = 0L,
    var id                  : String = ""
) : Serializable