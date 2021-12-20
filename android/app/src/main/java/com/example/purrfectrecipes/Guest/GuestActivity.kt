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
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.purrfectrecipes.Customer.HomeFragment
import com.example.purrfectrecipes.Customer.HomeFragmentViewModel
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuestActivity : AppCompatActivity() {
    private val homeViewModel:RecipesHomeViewModel by viewModels()
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
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
        }
    }

    override fun onBackPressed() {
        if(homeViewModel.getSort().value!=null && homeViewModel.getSort().value==true)
            homeViewModel.setSort(false)
        else if(homeViewModel.getFilter().value!=null && homeViewModel.getFilter().value==true)
            homeViewModel.setFilter(false)
        else
            super.onBackPressed()
    }






}