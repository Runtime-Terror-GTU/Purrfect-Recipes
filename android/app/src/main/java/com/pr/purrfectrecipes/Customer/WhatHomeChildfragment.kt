package com.pr.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Adapters.ChosenIngredientsRVAdapter
import com.pr.purrfectrecipes.R

class WhatHomeChildfragment: Fragment(R.layout.childfragment_home_what)
{
    private val viewModel: WhatHomeViewModel by activityViewModels()
    private val whatresViewModel:WhatresHomeViewModel by activityViewModels()

    private var wantedIngredientsRVAdapter:ChosenIngredientsRVAdapter?=null
    private var notWantedIngredientsRVAdapter:ChosenIngredientsRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                whatresViewModel.setView(null)
                viewModel.setShowResult(false)
                viewModel.setView(view)
                viewModel.resetIngredients()
                super.onViewCreated(view, savedInstanceState)
            }
        })

        setRVAdapter()

        val editWantedIngredientsButton=view.findViewById<LinearLayout>(R.id.editWantedIngredients)
        val editNotWantedIngredientsButton=view.findViewById<LinearLayout>(R.id.editNotWantedIngredients)
        editWantedIngredientsButton.setOnClickListener {
            viewModel.setEditWanted(true)
        }
        editNotWantedIngredientsButton.setOnClickListener {
            viewModel.setEditNotWanted(true)
        }

        viewModel.getWantedIngredients().observe(viewLifecycleOwner,{
            if(viewModel.getWantedIngredients().value!=null)
            {
                wantedIngredientsRVAdapter?.setIngredients(viewModel.getWantedIngredients().value!!)
                wantedIngredientsRVAdapter?.notifyDataSetChanged()
            }
        })

        viewModel.getNotWantedIngredients().observe(viewLifecycleOwner,{
            if(viewModel.getNotWantedIngredients().value!=null)
            {
                notWantedIngredientsRVAdapter?.setIngredients(viewModel.getNotWantedIngredients().value!!)
                notWantedIngredientsRVAdapter?.notifyDataSetChanged()
            }
        })

        val showRecipesButton=view.findViewById<LinearLayout>(R.id.showRecipes)
        showRecipesButton.setOnClickListener {
            if(viewModel.getWantedIngredients().value!!.size==0 && viewModel.getNotWantedIngredients().value!!.size==0)
                Toast.makeText(requireContext(), "You need to have chosen at least one wanted ingredient to continue.", Toast.LENGTH_SHORT).show()
            else
                viewModel.setShowResult(true)
        }
    }

    fun setRVAdapter()
    {
        val wantedIngredients = view?.findViewById<RecyclerView>(R.id.wantedIngredientsList)
        wantedIngredients?.layoutManager = GridLayoutManager(requireContext(), 3)
        wantedIngredientsRVAdapter = ChosenIngredientsRVAdapter(requireContext())
        wantedIngredients?.adapter =  wantedIngredientsRVAdapter

        val notWantedIngredients = view?.findViewById<RecyclerView>(R.id.notWantedIngredientsList)
        notWantedIngredients?.layoutManager = GridLayoutManager(requireContext(), 3)
        notWantedIngredientsRVAdapter = ChosenIngredientsRVAdapter(requireContext())
        notWantedIngredients?.adapter =  notWantedIngredientsRVAdapter
    }

}