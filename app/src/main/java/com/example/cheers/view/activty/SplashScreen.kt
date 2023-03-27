package com.example.cheers.view.activty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.lifecycleScope
import com.example.cheers.R
import com.example.cheers.databinding.ActivityMainBinding
import com.example.cheers.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
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