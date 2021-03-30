package com.example.yshop.splashfragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.yshop.R

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // open the main activity after splash fragment
        @Suppress("DEPRECATION")
        Handler().postDelayed(
                {
                    // Launch the main activity
                    try{
                        findNavController().navigate(R.id.action_splashFragment_to_logInFragment)
                    }catch (e:Exception){

                    }
                    // Call this when your activity is done and should be closed
                },2500
        )
    }

}