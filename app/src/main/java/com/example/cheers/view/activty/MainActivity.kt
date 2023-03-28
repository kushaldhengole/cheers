package com.example.cheers.view.activty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cheers.R
import com.example.cheers.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
   lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
       navController = findNavController(R.id.nav_host_fragment_activity_main2)
        navView.setupWithNavController(navController)

    }


    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.navView.isVisible = !(destination.id == R.id.navigation_detail)
        }

    }

    override fun onBackPressed() {
        navController.currentDestination?.let {
            navDestination ->
            if (navDestination.id==R.id.navigation_dashboard){
                finish()
            }
            else{
                super.onBackPressed()
            }
        }

    }
}