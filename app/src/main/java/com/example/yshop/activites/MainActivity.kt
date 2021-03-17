package com.example.yshop.activites

import android.app.Dialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.yshop.R
import com.example.yshop.databinding.ActivityMainBinding
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

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.logInFragment,
            R.id.registerFragment,
            R.id.forgetPasswordFragment,
            R.id.homeFragment,
            R.id.dashBoardFragment,
            R.id.notificationsFragment))

        setupActionBarWithNavController(navController , appBarConfiguration)


        // Set bottom navigation when fragment needed visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.homeFragment           -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.dashBoardFragment      -> binding.bottomNavigation.visibility = View.VISIBLE
                R.id.notificationsFragment  -> binding.bottomNavigation.visibility = View.VISIBLE

                else -> binding.bottomNavigation.visibility = View.INVISIBLE
            }
        }

        // Show actionBar in some fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.splashFragment             -> supportActionBar?.hide()
                R.id.logInFragment              -> supportActionBar?.hide()
                R.id.registerFragment           -> supportActionBar?.hide()
                R.id.forgetPasswordFragment     -> supportActionBar?.hide()

                else -> supportActionBar?.show()

            }
        }

    }
}