package com.pr.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pr.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment: Fragment(R.layout.fragment_profile)
{
    private val viewModel: ProfileFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarProfile=view.findViewById<BottomNavigationView>(R.id.navigationTabbarProfile)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarProfile, navController)
        navigationTabbarProfile.setOnItemReselectedListener {
            if(it==navigationTabbarProfile.menu.getItem(0)) {
                val infoViewModel:InfoProfileViewModel by activityViewModels()
                infoViewModel.setView(null)
                navController.popBackStack(R.id.infoProfileChildfragment, true)
                navController.navigate(R.id.infoProfileChildfragment)
            }
            else if (it==navigationTabbarProfile.menu.getItem(1)){
                val addedrecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
                addedrecipesViewModel.setView(null)
                navController.popBackStack(R.id.addedrecipesProfileChildfragment, true)
                navController.navigate(R.id.addedrecipesProfileChildfragment)
            }
            else{
                val purrfectedrecipesViewModel:PurrfectedrecipesProfileViewModel by activityViewModels()
                purrfectedrecipesViewModel.setView(null)
                navController.popBackStack(R.id.purrfectedrecipesProfileChildfragment, true)
                navController.navigate(R.id.purrfectedrecipesProfileChildfragment)
            }
        }

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