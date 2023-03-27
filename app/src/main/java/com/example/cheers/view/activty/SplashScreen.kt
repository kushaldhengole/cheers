package com.example.cheers.view.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cheers.R
import com.example.cheers.databinding.ActivitySplashScreenBinding
import com.example.cheers.databinding.FragmentDetailBinding
import com.example.cheers.view.activty.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(200)
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            this@SplashScreen.finish()
        }
    }
}