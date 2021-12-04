package com.example.purrfectrecipes.Admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminFragment: Fragment(R.layout.fragment_admin)
{
    private val viewModel: AdminFragmentViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarAdmin=view.findViewById<BottomNavigationView>(R.id.navigationTabbarAdmin)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarAdmin, navController)

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
            super.onViewCreated(view, savedInstanceState)
    }

}