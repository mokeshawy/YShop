package com.example.yshop.settingsfragment

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yshop.datastoreoperetion.DataStoreRepository
import com.example.yshop.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    fun showData(context: Context ,
                 tvFirstLastName : TextView ,
                 tvGender : TextView ,
                 tvEmail : TextView ,
                 tvMobile : TextView ,
                 ivUserImage : ImageView){

        viewModelScope.launch {
            tvFirstLastName.text = "${ DataStoreRepository(context).showFirstName(Constants.FIRST_NAME_KEY)} ${DataStoreRepository(context).showLastName(Constants.LAST_NAME_KEY)}"
            tvGender.text = DataStoreRepository(context).showGender(Constants.USER_GENDER_KEY)
            tvEmail.text = DataStoreRepository(context).showUserEmail(Constants.USER_EMAIL_KEY)
            tvMobile.text = DataStoreRepository(context).showMobile(Constants.USER_MOBILE_KEY).toString()
            Picasso.get().load(DataStoreRepository(context).showUserImage(Constants.USER_IMAGE_KEY)).into(ivUserImage)
        }

    }
}