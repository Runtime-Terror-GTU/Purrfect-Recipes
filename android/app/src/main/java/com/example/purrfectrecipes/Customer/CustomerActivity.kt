package com.example.purrfectrecipes.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class CustomerActivity : AppCompatActivity() {

    private val homeViewModel:HomeFragmentViewModel by viewModels()
    private val profileViewModel:ProfileFragmentViewModel by viewModels()
    private val settingsViewModel: SettingsFragmentViewModel by viewModels()

    private val recipesViewModel:RecipesHomeViewModel by viewModels()

    var navHostFragment:NavHostFragment?=null
    var navController: NavController?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        val navigationBarCustomer=findViewById<BottomNavigationView>(R.id.navigationBarCustomer)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        NavigationUI.setupWithNavController(navigationBarCustomer, navController!!)
        navigationBarCustomer.setOnItemReselectedListener {
            if(it==navigationBarCustomer.menu.getItem(0)) {
                homeViewModel.setView(null)
                navController?.popBackStack(R.id.sortFragment, true)
                navController?.popBackStack(R.id.filterFragment, true)
                navController?.popBackStack(R.id.homeFragment, true)
                navController?.navigate(R.id.homeFragment)
            }else if(it==navigationBarCustomer.menu.getItem(1)) {
                profileViewModel.setView(null)
                navController?.popBackStack(R.id.profileFragment, true)
                navController?.navigate(R.id.profileFragment)
            }
            else{
                settingsViewModel.setView(null)
                navController?.popBackStack(R.id.settingsFragment, true)
                navController?.navigate(R.id.settingsFragment)
            }
        }

        recipesViewModel.getSort().observe(this,{
            if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.sortFragment)
            }
            else if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
        recipesViewModel.getFilter().observe(this, {
            if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.filterFragment)
            }
            else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
    }
    override fun onBackPressed() {

        if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true) {
                recipesViewModel.resetSort()
                recipesViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true) {
                recipesViewModel.setFilter(false)
        }
        else
            super.onBackPressed()
    }
}