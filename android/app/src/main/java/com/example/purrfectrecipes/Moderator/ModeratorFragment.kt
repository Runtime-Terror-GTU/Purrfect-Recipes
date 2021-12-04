package com.example.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Admin.AdminFragmentViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ModeratorFragment: Fragment(R.layout.fragment_moderator)
{
    private val viewModel: ModeratorFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarModerator=view.findViewById<BottomNavigationView>(R.id.navigationTabbarModerator)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarModerator, navController)

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
            super.onViewCreated(view, savedInstanceState)
    }

}