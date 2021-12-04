package com.example.purrfectrecipes.Admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Moderator.ModeratorFragmentViewModel
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemoveAdminChildfragment: Fragment(R.layout.childfragment_admin_remove)
{
    private val viewModel: RemoveAdminViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
            super.onViewCreated(view, savedInstanceState)
    }
}