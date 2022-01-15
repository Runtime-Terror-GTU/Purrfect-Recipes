package com.pr.purrfectrecipes.Admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pr.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminFragment: Fragment(R.layout.fragment_admin)
{
    private val viewModel: AdminFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarAdmin=view.findViewById<BottomNavigationView>(R.id.navigationTabbarAdmin)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarAdmin, navController)
        navigationTabbarAdmin.setOnItemReselectedListener {
            if(it==navigationTabbarAdmin.menu.getItem(0)) {
                val addAdminViewModel:AddAdminViewModel by viewModels()
                addAdminViewModel.setView(null)
                navController.popBackStack(R.id.addAdminChildfragment, true)
                navController.navigate(R.id.addAdminChildfragment)
            }
            else {
                val removeAdminViewModel:RemoveAdminViewModel by viewModels()
                removeAdminViewModel.setView(null)
                navController.popBackStack(R.id.removeAdminChildfragment, true)
                navController.navigate(R.id.removeAdminChildfragment)
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