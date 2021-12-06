package com.example.purrfectrecipes.Guest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.purrfectrecipes.Customer.HomeFragment
import com.example.purrfectrecipes.Customer.HomeFragmentViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        val navigationBarGuest=findViewById<BottomNavigationView>(R.id.navigationBarGuest)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationBarGuest, navController)
        navigationBarGuest.setOnItemReselectedListener {
            if(it==navigationBarGuest.menu.getItem(0)) {
                val homeViewModel:HomeFragmentViewModel by viewModels()
                homeViewModel.setView(null)
                navController.navigate(R.id.homeFragment)
            }
        }


    }






}