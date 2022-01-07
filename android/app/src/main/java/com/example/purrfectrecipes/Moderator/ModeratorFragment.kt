package com.example.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Admin.AdminFragmentViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ModeratorFragment: Fragment(R.layout.fragment_moderator)
{
    private val viewModel: ModeratorFragmentViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navigationTabbarModerator=view.findViewById<BottomNavigationView>(R.id.navigationTabbarModerator)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationTabbarModerator, navController)
        navigationTabbarModerator.setOnItemReselectedListener {
            if(it==navigationTabbarModerator.menu.getItem(0)) {
                val usersViewModel:UsersModeratorViewModel by activityViewModels()
                usersViewModel.setView(null)
                navController.popBackStack(R.id.usersModeratorChildfragment, true)
                navController.navigate(R.id.usersModeratorChildfragment)
            }
            else if (it==navigationTabbarModerator.menu.getItem(1)){
                val recipesViewModel:RecipesModeratorViewModel by activityViewModels()
                recipesViewModel.setView(null)
                navController.popBackStack(R.id.recipesModeratorChildfragment, true)
                navController.navigate(R.id.recipesModeratorChildfragment)
            }
            else{
                val requestsViewModel:RequestsModeratorViewModel by activityViewModels()
                requestsViewModel.setView(null)
                navController.popBackStack(R.id.requestsModeratorChildfragment, true)
                navController.navigate(R.id.requestsModeratorChildfragment)
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