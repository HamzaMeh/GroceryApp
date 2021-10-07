package com.archestro.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.archestro.grocery.databinding.ActivityMainBinding
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        navController= Navigation.findNavController(this,R.id.nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}