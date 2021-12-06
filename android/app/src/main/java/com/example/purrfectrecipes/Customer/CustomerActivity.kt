package com.example.purrfectrecipes.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class CustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        val navigationBarCustomer=findViewById<BottomNavigationView>(R.id.navigationBarCustomer)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationBarCustomer, navController)
        navigationBarCustomer.setOnItemReselectedListener {
            if(it==navigationBarCustomer.menu.getItem(0)) {
                val homeViewModel:HomeFragmentViewModel by viewModels()
                homeViewModel.setView(null)
                navController.navigate(R.id.homeFragment)
            }
            else if(it==navigationBarCustomer.menu.getItem(1)) {
                val profileViewModel:ProfileFragmentViewModel by viewModels()
                profileViewModel.setView(null)
                navController.navigate(R.id.profileFragment)

            }
            else{
                val settingsViewModel: SettingsFragmentViewModel by viewModels()
                settingsViewModel.setView(null)
                navController.navigate(R.id.settingsFragment)
            }
        }


    }
}