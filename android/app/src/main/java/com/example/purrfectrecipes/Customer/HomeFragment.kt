package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment: Fragment(R.layout.fragment_home)
{
    private val viewModel:HomeFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarHome=view.findViewById<BottomNavigationView>(R.id.navigationTabbarHome)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarHome, navController)
        navigationTabbarHome.setOnItemReselectedListener {
            if(it==navigationTabbarHome.menu.getItem(0)) {
                val recipesViewModel:RecipesHomeViewModel by viewModels()
                recipesViewModel.setView(null)
                navController.navigate(R.id.recipesHomeChildfragment)
            }
            else {
                val whatViewModel:WhatHomeViewModel by viewModels()
                whatViewModel.setView(null)
                navController.navigate(R.id.whatHomeChildfragment)
            }
        }

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
        {
            viewModel.setView(view)
            super.onViewCreated(view, savedInstanceState)
        }
    }


}