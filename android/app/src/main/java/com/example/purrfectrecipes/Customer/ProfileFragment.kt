package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Moderator.ModeratorFragmentViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment: Fragment(R.layout.fragment_profile)
{
    private val viewModel: ProfileFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarProfile=view.findViewById<BottomNavigationView>(R.id.navigationTabbarProfile)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarProfile, navController)
        navigationTabbarProfile.setOnItemReselectedListener {
            if(it==navigationTabbarProfile.menu.getItem(0)) {
                val infoViewModel:InfoProfileViewModel by viewModels()
                infoViewModel.setView(null)
                navController.navigate(R.id.infoProfileChildfragment)
            }
            else if (it==navigationTabbarProfile.menu.getItem(1)){
                val addedrecipesViewModel:AddedrecipesProfileViewModel by viewModels()
                addedrecipesViewModel.setView(null)
                navController.navigate(R.id.addedrecipesProfileChildfragment)
            }
            else{
                val purrfectedrecipesViewModel:PurrfectedrecipesProfileViewModel by viewModels()
                purrfectedrecipesViewModel.setView(null)
                navController.navigate(R.id.purrfectedrecipesProfileChildfragment)
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