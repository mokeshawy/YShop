package com.example.yshop.activites

import android.app.Dialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.yshop.R
import com.example.yshop.databinding.ActivityMainBinding
import com.example.yshop.core.utils.OptionBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.grpc.InternalChannelz.id

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main )

        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById( R.id.nav_host_fragment ) as NavHostFragment
        val navController   : NavController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(navController)

        supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.app_gradinet_color_background))

        val appBarConfiguration = AppBarConfiguration(setOf( R.id.dashBoardFragment,
            R.id.productsFragment,
            R.id.ordersFragment ,
            R.id.soldProductFragment))

        setupActionBarWithNavController(navController , appBarConfiguration)


        // Set bottom navigation when fragment needed visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){

                R.id.dashBoardFragment       -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.productsFragment        -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.ordersFragment          -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.soldProductFragment     -> binding.bottomNavigation.visibility = View.VISIBLE

                else -> binding.bottomNavigation.visibility = View.INVISIBLE
            }
        }

        // Show actionBar in some fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.splashFragment                 -> supportActionBar?.hide()
                R.id.logInFragment                  -> supportActionBar?.hide()
                R.id.registerFragment               -> supportActionBar?.hide()
                R.id.forgetPasswordFragment         -> supportActionBar?.hide()
                R.id.settingsFragment               -> supportActionBar?.hide()
                R.id.userCompleteProfileFragment    -> supportActionBar?.hide()
                R.id.addProductFragment             -> supportActionBar?.hide()
                R.id.productDetailsFragment         -> supportActionBar?.hide()
                R.id.cartListFragment               -> supportActionBar?.hide()
                R.id.addressListFragment            -> supportActionBar?.hide()
                R.id.addEditAddressFragment         -> supportActionBar?.hide()
                R.id.checkOutFragment               -> supportActionBar?.hide()
                R.id.orderDetailsFragment           -> supportActionBar?.hide()
                R.id.soldProductDetailsFragment     -> supportActionBar?.hide()

                else -> supportActionBar?.show()

            }
        }

    }
}