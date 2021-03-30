package com.example.yshop.usercompleteprofile

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
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.fragment.app.viewModels
import com.example.yshop.R
import com.example.yshop.databinding.FragmentUserCompleteProfileBinding
import com.example.yshop.datastoreoperetion.DataStoreRepository
import com.example.yshop.utils.Constants
import com.example.yshop.utils.OptionBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
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


        // Show details for user because not change this details
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

        // Check user entry from new user needed profile complete or entry from setting to edit profile
        CoroutineScope(Dispatchers.Main).async {

            var profileComplete = DataStoreRepository(requireActivity()).showProfileComplete(Constants.COMPLETE_PROFILE).toString()

            if( profileComplete.toInt() == 0 ){
                binding.tvTitleId.text = resources.getString(R.string.title_complete_profile)

                binding.etFirstNameId.isEnabled = false
                binding.etLastNameId.isEnabled  = false

            }else{
                Picasso.get().load(DataStoreRepository(requireActivity()).showUserImage(Constants.USER_IMAGE_KEY)).into(binding.ivUserPhotoId)
                binding.tvTitleId.text = resources.getString(R.string.title_edit_profile)

                binding.etFirstNameId.isEnabled = true
                binding.etLastNameId.isEnabled  = true
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