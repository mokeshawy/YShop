package com.example.yshop.orderdetailsfragment

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yshop.R
import com.example.yshop.model.CartItemModel
import com.example.yshop.model.OrderModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class OrderDetailsViewModel : ViewModel(){

    var cartListAdapter = MutableLiveData<ArrayList<CartItemModel>>()

    @SuppressLint("SetTextI18n")
    fun setUpUi(context : Context,
                orderModel : OrderModel,
                tv_order_details_id : TextView,
                tv_order_details_date : TextView,
                tv_order_status : TextView,
                tv_my_order_details_address_type : TextView,
                tv_my_order_details_full_name : TextView,
                tv_my_order_details_address : TextView,
                tv_my_order_details_additional_note : TextView,
                tv_my_order_details_other_details : TextView,
                tv_my_order_details_mobile_number : TextView,
                tv_order_details_sub_total : TextView,
                tv_order_details_shipping_charge : TextView,
                tv_order_details_total_amount : TextView){

        tv_order_details_id.text = orderModel.title

        // Date Format in which the date will be displayed in the UI.
        val dataFormat = "dd MMM yyyy HH:mm"

        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat( dataFormat , Locale.getDefault())

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calender = Calendar.getInstance()
        calender.timeInMillis = orderModel.orderDataTime

        val orderDataTime = formatter.format(calender.time)
        tv_order_details_date.text = orderDataTime

        // Get the difference between the order date time and current date time in hours.
        // If the difference in hours is 1 or less then the order status will be PENDING.
        // If the difference in hours is 2 or greater then 1 then the order status will be PROCESSING.
        // And, if the difference in hours is 3 or greater then the order status will be DELIVERED.

        val diffInMilliSecond : Long =  System.currentTimeMillis() - orderModel.orderDataTime
        val diffInHours : Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSecond)

        when{
            diffInHours < 1 ->{
                tv_order_status.text = context.resources.getString(R.string.order_status_pending)
                tv_order_status.setTextColor(ContextCompat.getColor(context , R.color.colorAccent))
            }
            diffInHours < 2 ->{
                tv_order_status.text = context.resources.getString(R.string.order_status_in_process)
                tv_order_status.setTextColor(ContextCompat.getColor(context , R.color.colorOrderStatusInProcess))
            }
            else ->{
                tv_order_status.text = context.resources.getString(R.string.order_status_delivered)
                tv_order_status.setTextColor(ContextCompat.getColor(context , R.color.colorOrderStatusDelivered))
            }
        }

        // Show order item in recycler view
        cartListAdapter.value = orderModel.items

        tv_my_order_details_address_type.text       = orderModel.address.type
        tv_my_order_details_full_name.text          = orderModel.address.name
        tv_my_order_details_address.text            = "${orderModel.address.address}, ${orderModel.address.zipCode}"
        tv_my_order_details_additional_note.text    = orderModel.address.additionalNote

        if( orderModel.address.otherDetails.isNotEmpty()){
            tv_my_order_details_other_details.visibility    = View.VISIBLE
            tv_my_order_details_other_details.text          = orderModel.address.otherDetails
        }else{
            tv_my_order_details_other_details.visibility = View.GONE
        }

        tv_my_order_details_mobile_number.text  = orderModel.address.mobileNumber
        tv_order_details_sub_total.text         = "$${orderModel.subTotalAmount}"
        tv_order_details_shipping_charge.text   = "$${orderModel.shippingCharge}"
        tv_order_details_total_amount.text      = "$${orderModel.totalAmount}"
    }
}