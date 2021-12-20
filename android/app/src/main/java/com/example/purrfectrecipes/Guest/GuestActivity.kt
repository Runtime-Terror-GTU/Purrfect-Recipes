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
import com.example.purrfectrecipes.FilterFragment
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.SortFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class GuestActivity : AppCompatActivity() {

    private val recipesViewModel:RecipesHomeViewModel by viewModels()
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
                navController.popBackStack(R.id.sortFragment, true)
                navController.popBackStack(R.id.filterFragment, true)
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
        }
        recipesViewModel.getSort().observe(this,{
            if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true)
            {
                navController.popBackStack(R.id.sortFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController.navigate(R.id.sortFragment)
            }
            else if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==false){
                navController.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
            }
        })
        recipesViewModel.getFilter().observe(this, {
            if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            {
                navController.popBackStack(R.id.filterFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController.navigate(R.id.filterFragment)
            }
            else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==false){
                navController.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
            }
        })
    }

    override fun onBackPressed() {
        if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true) {
            recipesViewModel.setSort(false)
        }
        else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            recipesViewModel.setFilter(false)
        else
            super.onBackPressed()
    }






}