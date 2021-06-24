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
import androidx.navigation.fragment.findNavController
import com.example.yshop.R
import com.example.yshop.databinding.FragmentAddProductBinding
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder

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

        // Connect whit view model
        binding.lifecycleOwner = this
        binding.addProductVarModel = addProductViewModel

        // ToolBar button back from add product to product
        binding.toolbarAddProductFragment.setNavigationIcon(R.drawable.ic_white_color_back__24)
        binding.toolbarAddProductFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_productsFragment)
        }

        binding.btnSubmit.setOnClickListener {

            try {
                addProductViewModel.addProductDetails( requireActivity() , view , imageUri)
            }catch (e:Exception){
                OptionBuilder.showErrorSnackBar(resources.getString(R.string.err_msg_select_product_image), true , requireActivity() , view)
            }
        }


        // Select image from add product page
        binding.ivAddUpdateProduct.setOnClickListener {
            // Request permission for open storage for your phone
            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }else{

                var intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent,Constants.PICK_IMAGE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == Constants.PICK_IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            if(data !=null){
                try{
                    // when user select image change button select to edit image
                    binding.ivAddUpdateProduct.setImageDrawable(ContextCompat.getDrawable(requireActivity(),R.drawable.ic_vector_edit))

                    imageUri = data?.data!!
                    binding.ivProductImage.setImageURI(imageUri)
                }catch(e:Exception){
                    Toast.makeText(requireActivity(),resources.getString(R.string.image_selection_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}