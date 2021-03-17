package com.example.yshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yshop.activites.MainActivity

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //findNavController().navigate(R.id.action_splashFragment_to_logInFragment)

        // open the main activity after splash activity
        @Suppress("DEPRECATION")
        Handler().postDelayed(
                {
                    // Launch the main activity
                    findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
                    // Call this when your activity is done and should be closed
                },2500
        )
    }

}