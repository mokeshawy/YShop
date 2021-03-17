package com.example.yshop.userprofile

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentUserCompleteProfileBinding
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import java.io.IOException

class UserCompleteProfileFragment : Fragment() {

    lateinit var binding            : FragmentUserCompleteProfileBinding
    val userCompleteProfileViewMode : UserCompleteProfileViewMode by viewModels()
    lateinit var imageUri           : Uri
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentUserCompleteProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit userCompleteProfileVarMode
        binding.lifecycleOwner = this
        binding.userCompleteProfileVarMode = userCompleteProfileViewMode

        // hide the action bar
        //(activity as AppCompatActivity).supportActionBar?.hide()


        // Show details for user because not change this details
        binding.etFirstNameId.isEnabled = false
        binding.etLastNameId.isEnabled  = false
        binding.etEmailId.isEnabled     = false
        userCompleteProfileViewMode.showData(requireActivity() , binding.etFirstNameId , binding.etLastNameId , binding.etEmailId)


        // Select user photo
        binding.ivUserPhotoId.setOnClickListener {
            // Request permission for open storage for your phone
            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }else{

                var intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent,Constants.PICK_IMAGE_REQUEST_CODE)
            }
        }


        // button save update profile
        binding.btnSubmitId.setOnClickListener {
            // using try if user not selected image show error message
            try {
                // Call function for update profile from viewModel
                userCompleteProfileViewMode.completeProfile(requireActivity() , view , imageUri , binding.rbMaleId )
            }catch (e: Exception){
                OptionBuilder.showErrorSnackBar(resources.getString(R.string.image_selection_failed),true, requireActivity() , view)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            if( data !=null){
                try {
                    imageUri = data?.data!!
                    binding.ivUserPhotoId.setImageURI(imageUri)
                }catch (e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireActivity(),resources.getString(R.string.image_selection_failed),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}