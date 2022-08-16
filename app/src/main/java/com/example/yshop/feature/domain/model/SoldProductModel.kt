package com.example.yshop.feature.domain.model

import com.example.yshop.feature.addresslistfragment.data.model.request.AddressModel
import java.io.Serializable

data class SoldProductModel (

    var userId          : String = "",
    var title           : String = "",
    var price           : String = "",
    var soldQuantity    : String = "",
    var image           : String = "",
    var orderId         : String = "",
    var orderData       : Long = 0L,
    var subTotalAmount  : String = "",
    var shippingCharge  : String = "",
    var totalAmount     : String = "",
    var addressModel    : AddressModel = AddressModel(),
    var id              : String = ""

) : Serializable