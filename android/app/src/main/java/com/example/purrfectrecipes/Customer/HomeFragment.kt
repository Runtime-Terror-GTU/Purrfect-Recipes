package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment: Fragment(R.layout.fragment_home)
{
    private val viewModel:HomeFragmentViewModel by activityViewModels()
    private val recipesViewModel:RecipesHomeViewModel by activityViewModels()
    private val whatViewModel:WhatHomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarHome=view.findViewById<BottomNavigationView>(R.id.navigationTabbarHome)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarHome, navController)
        navigationTabbarHome.setOnItemReselectedListener {
            if(it==navigationTabbarHome.menu.getItem(0)) {
                recipesViewModel.setView(null)
                navController.popBackStack(R.id.recipesHomeChildfragment, true)
                navController.navigate(R.id.recipesHomeChildfragment)
            }
            else {
                whatViewModel.setView(null)
                navController.popBackStack(R.id.whatHomeChildfragment, true)
                navController.navigate(R.id.whatHomeChildfragment)
            }
        }

        whatViewModel.getShowResults().observe(viewLifecycleOwner,{
            val current= navHostFragment?.childFragmentManager?.fragments?.get(0)
            if(whatViewModel.getShowResults().value!=null && whatViewModel.getShowResults().value==true && current is WhatHomeChildfragment)
            {
                navController?.popBackStack(R.id.whatresHomeChildfragment, true)
                navController?.navigate(R.id.action_whatHomeChildfragment_to_whatresHomeChildfragment)
            }
            else if(whatViewModel.getShowResults().value!=null && whatViewModel.getShowResults().value==false){
                navController?.popBackStack(R.id.whatHomeChildfragment, false)
            }
        })

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })
    }

}