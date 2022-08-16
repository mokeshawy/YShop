package com.example.yshop.feature.soldproductdetailsfragment.domain.viewmodel


import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.yshop.feature.domain.model.SoldProductModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class SoldProductDetailsViewModel : ViewModel() {


    @SuppressLint("SetTextI18n")
    fun setUpUi(
        soldProductModel: SoldProductModel,
        tv_sold_product_details_id: TextView,
        tv_sold_product_details_date: TextView,
        iv_product_item_image: ImageView,
        tv_product_item_name: TextView,
        tv_product_item_price: TextView,
        tv_sold_product_quantity: TextView,
        tv_sold_details_address_type: TextView,
        tv_sold_details_full_name: TextView,
        tv_sold_details_address: TextView,
        tv_sold_details_additional_note: TextView,
        tv_sold_details_other_details: TextView,
        tv_sold_details_mobile_number: TextView,
        tv_sold_product_sub_total: TextView,
        tv_sold_product_shipping_charge: TextView,
        tv_sold_product_total_amount: TextView
    ) {

        tv_sold_product_details_id.text = soldProductModel.orderId

        // Date Format in which the date will be displayed in the UI.
        var dataFormat = "dd MM yyy HH:mm"

        // Create a DateFormatter object for displaying date in specified format.
        var formatter = SimpleDateFormat(dataFormat, Locale.getDefault())

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        var calender = Calendar.getInstance()
        calender.timeInMillis = soldProductModel.orderData

        tv_sold_product_details_date.text = formatter.format(calender.time)

        Picasso.get().load(soldProductModel.image).into(iv_product_item_image)

        tv_product_item_name.text = soldProductModel.title
        tv_product_item_price.text = "$${soldProductModel.price}"
        tv_sold_product_quantity.text = soldProductModel.soldQuantity

        tv_sold_details_address_type.text = soldProductModel.addressModel.type
        tv_sold_details_full_name.text = soldProductModel.addressModel.name
        tv_sold_details_address.text =
            "${soldProductModel.addressModel.address}, ${soldProductModel.addressModel.zipCode}"
        tv_sold_details_additional_note.text = soldProductModel.addressModel.additionalNote

        if (soldProductModel.addressModel.otherDetails.isNotEmpty()) {
            tv_sold_details_other_details.visibility = View.VISIBLE
            tv_sold_details_other_details.text = soldProductModel.addressModel.otherDetails
        } else {
            tv_sold_details_other_details.visibility = View.GONE
        }

        tv_sold_details_mobile_number.text = soldProductModel.addressModel.mobileNumber

        tv_sold_product_sub_total.text = "$${soldProductModel.subTotalAmount}"
        tv_sold_product_shipping_charge.text = "$${soldProductModel.shippingCharge}"
        tv_sold_product_total_amount.text = "$${soldProductModel.totalAmount}"


    }
}