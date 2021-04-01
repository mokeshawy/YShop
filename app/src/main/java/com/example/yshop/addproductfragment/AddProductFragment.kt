package com.example.yshop.addproductfragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentAddProductBinding
import com.example.yshop.utils.Constants

class AddProductFragment : Fragment() {

    lateinit var binding    : FragmentAddProductBinding
    val addProductViewModel : AddProductViewModel by viewModels()
    lateinit var imageUri   : Uri
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.addProductVarModel = addProductViewModel


        binding.ivAddUpdateProduct.setOnClickListener {
            // Request permission for open storage for your phone
            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }else{

                var intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent,Constants.PICK_IMAGE_REQUEST_CODE2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == Constants.PICK_IMAGE_REQUEST_CODE2 && resultCode == AppCompatActivity.RESULT_OK){
            if(data !=null){
                try{
                    imageUri = data?.data!!
                    binding.ivProductImage.setImageURI(imageUri)
                }catch(e:Exception){
                    Toast.makeText(requireActivity(),resources.getString(R.string.image_selection_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}