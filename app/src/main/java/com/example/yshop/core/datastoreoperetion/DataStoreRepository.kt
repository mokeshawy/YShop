package com.example.yshop.core.datastoreoperetion

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import com.example.yshop.core.utils.Constants
import kotlinx.coroutines.flow.first

class DataStoreRepository( context: Context) {

    var dataStore = context.createDataStore(name = Constants.DATA_STORE_NAME)

    // Get firstName from dataStore
    suspend fun showFirstName( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    // Get lastName from dataStore
    suspend fun showLastName( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    // Get gender from dataStore
    suspend fun showGender( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    // Get userEmail from dataStore
    suspend fun showUserEmail( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }


    // Get mobile from dataStore
    suspend fun showMobile( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    // Get userImage from dataStore
    suspend fun showUserImage( key : String ) : String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }


    // Get profileComplete from dataStore
    suspend fun showProfileComplete( key : String ) : Int?{
        val dataStoreKey = preferencesKey<Int>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
}