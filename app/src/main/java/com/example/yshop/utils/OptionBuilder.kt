package com.example.yshop.utils

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.yshop.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

object OptionBuilder {


    // fun snack bar show error and successful
    fun showErrorSnackBar(message : String, errorMessage : Boolean, context: Context, view : View){

        val snackBar = Snackbar.make( view , message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor( context , R.color.colorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(context , R.color.colorSnackBarSuccess))
        }
        snackBar.show()
    }

    lateinit var mProgressDialog : Dialog
    fun showProgressDialog( text : String ,context: Context){

        mProgressDialog = Dialog(context)

        /* Set the screen content from a layout resource
        The resource will be inflated , adding all top-level views to the screen */
        mProgressDialog.setContentView(R.layout.dialog_progress)
        var dialog = mProgressDialog.findViewById(R.id.tv_progress_textId) as TextView
        dialog.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        mProgressDialog.show()

    }

    // hide progress bar
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}