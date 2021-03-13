package com.example.yshop.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.yshop.R
import com.example.yshop.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_splash
        )


        // open activity whit out Action Bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // open the main activity after splash activity
        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                // Launch the main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Call this when your activity is done and should be closed
            },2500
        )


    }
}