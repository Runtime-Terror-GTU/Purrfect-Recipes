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

import android.widget.ImageButton
import androidx.fragment.app.activityViewModels


class SettingsFragment: Fragment(R.layout.fragment_settings)
{
    private val viewModel: SettingsFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarSettings=view.findViewById<BottomNavigationView>(R.id.navigationTabbarSettings)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarSettings, navController)
        navigationTabbarSettings.setOnItemReselectedListener {
            val settingsViewModel:SettingsSharedViewModel by activityViewModels()
            if(it==navigationTabbarSettings.menu.getItem(0)) {
                settingsViewModel.setVerifyView(null)
                navController.popBackStack(R.id.getverifiedSettingsChildfragment, true)
                navController.navigate(R.id.getverifiedSettingsChildfragment)
            }
            else if(it==navigationTabbarSettings.menu.getItem(1)) {
                settingsViewModel.setShopView(null)
                navController.popBackStack(R.id.shopSettingsChildFragment, true)
                navController.navigate(R.id.shopSettingsChildFragment)
            }
            else if (it==navigationTabbarSettings.menu.getItem(2)){
                settingsViewModel.setSuggestView(null)
                navController.popBackStack(R.id.suggestSettingsChildfragment, true)
                navController.navigate(R.id.suggestSettingsChildfragment)
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