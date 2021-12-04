package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R

class RecipesHomeChildfragment: Fragment(R.layout.childfragment_home_recipes)
{
    private val viewModel: RecipesHomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
            super.onViewCreated(view, savedInstanceState)
    }

}