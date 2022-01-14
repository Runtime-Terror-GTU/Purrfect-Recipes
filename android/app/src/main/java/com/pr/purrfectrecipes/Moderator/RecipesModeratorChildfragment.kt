package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pr.purrfectrecipes.R

class RecipesModeratorChildfragment: Fragment(R.layout.childfragment_moderator_recipes)
{
    private val viewModel:RecipesModeratorViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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