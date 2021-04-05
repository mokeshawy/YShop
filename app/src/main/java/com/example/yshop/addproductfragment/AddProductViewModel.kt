package com.example.yshop.addproductfragment

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yshop.R
import com.example.yshop.datastoreoperetion.DataStoreRepository
import com.example.yshop.model.ProductModel
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch

class AddProductViewModel : ViewModel() {

    var etProductTitle          = MutableLiveData<String>("")
    var etProductPrice          = MutableLiveData<String>("")
    var etProductDescription    = MutableLiveData<String>("")
    var etProductQuantity       = MutableLiveData<String>("")

    fun validateInput( context : Context , view : android.view.View ) : Boolean{
        return when {

            TextUtils.isEmpty(etProductTitle.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_product_title),true , context , view )
                false
            }
            TextUtils.isEmpty(etProductPrice.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_product_price) , true , context , view)
                false
            }
            TextUtils.isEmpty(etProductDescription.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_product_description) , true , context , view)
                false
            }
            TextUtils.isEmpty(etProductQuantity.value.toString().trim { it <=' ' }) ->{
                OptionBuilder.showErrorSnackBar(context.getString(R.string.err_msg_enter_product_quantity) , true , context , view)
                false
            }
            else ->{
                true
            }
        }
    }


    var firebaseDataBase    = FirebaseDatabase.getInstance()
    var productReference    = firebaseDataBase.getReference(Constants.PRODUCT)
    var productStorage      = FirebaseStorage.getInstance().reference

    // Fun add new product
    fun addProductDetails( context : Context , view: View , imageUri : Uri ){

        // Validate input for add product page
        if(validateInput( context , view )){

            // Show progressDialog
            OptionBuilder.showProgressDialog(context.resources.getString(R.string.please_wait),context)

            var refStorage : StorageReference = productStorage.child("ProductPhoto/"+Constants.PRODUCT_IMAGE_FILE+System.currentTimeMillis())
            refStorage.putFile(imageUri).addOnCompleteListener { saveImage ->

                if(saveImage.isSuccessful){
                    OptionBuilder.hideProgressDialog()
                    refStorage.downloadUrl.addOnSuccessListener { imageUri ->

                        viewModelScope.launch {
                            var firstName   = DataStoreRepository(context).showFirstName(Constants.FIRST_NAME_KEY)
                            var lastName    = DataStoreRepository(context).showLastName(Constants.LAST_NAME_KEY)
                            var userName = "${firstName} ${lastName}"

                            var product = ProductModel(
                                    Constants.getCurrentUser(),
                                    userName,
                                    etProductTitle.value!!.trim { it <=' ' },
                                    etProductPrice.value!!.trim { it <=' ' },
                                    etProductDescription.value!!.trim { it <=' ' },
                                    etProductQuantity.value!!.trim { it <=' ' },
                                    imageUri.toString() )
                            productReference.push().setValue(product)
                            Toast.makeText(context , context.getString(R.string.product_uploaded_success_message),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    OptionBuilder.showErrorSnackBar("Error for uploaded product image " , true , context , view)
                }
            }
        }
    }
}