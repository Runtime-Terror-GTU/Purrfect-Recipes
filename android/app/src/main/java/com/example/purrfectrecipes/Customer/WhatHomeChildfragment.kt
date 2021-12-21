package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R

class WhatHomeChildfragment: Fragment(R.layout.childfragment_home_what)
{
    private val viewModel: WhatHomeViewModel by activityViewModels()
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

        val editWantedIngredientsButton=view.findViewById<LinearLayout>(R.id.editWantedIngredients)
        editWantedIngredientsButton.setOnClickListener {
            viewModel.setEditWanted(true)
        }
    }

}