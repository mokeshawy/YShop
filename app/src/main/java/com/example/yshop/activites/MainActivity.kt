package com.example.yshop.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.yshop.R
import com.example.yshop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main )

        val navHostFragment : NavHostFragment = supportFragmentManager.findFragmentById( R.id.nav_host_fragment ) as NavHostFragment
        val navController   : NavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.logInFragment,
                                                            R.id.registerFragment))
        setupActionBarWithNavController(navController , appBarConfiguration)
    }
}