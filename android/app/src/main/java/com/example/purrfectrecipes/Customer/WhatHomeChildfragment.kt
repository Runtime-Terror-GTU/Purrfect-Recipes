package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.ChosenIngredientsRVAdapter
import com.example.purrfectrecipes.Adapters.IngredientsRVAdapter
import com.example.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.example.purrfectrecipes.EditIngredientsViewModel
import com.example.purrfectrecipes.R

class WhatHomeChildfragment: Fragment(R.layout.childfragment_home_what)
{
    private val viewModel: WhatHomeViewModel by activityViewModels()
    private val editIngredientsViewModel:EditIngredientsViewModel by activityViewModels()

    private var wantedIngredientsRVAdapter:ChosenIngredientsRVAdapter?=null
    private var notWantedIngredientsRVAdapter:ChosenIngredientsRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                editIngredientsViewModel.resetIngredients()
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

        editIngredientsViewModel.getWantedIngredients().observe(viewLifecycleOwner,{
            if(editIngredientsViewModel.getWantedIngredients().value!=null)
            {
                wantedIngredientsRVAdapter?.setIngredients(editIngredientsViewModel.getWantedIngredients().value!!)
                wantedIngredientsRVAdapter?.notifyDataSetChanged()
            }
        })

        editIngredientsViewModel.getNotWantedIngredients().observe(viewLifecycleOwner,{
            if(editIngredientsViewModel.getNotWantedIngredients().value!=null)
            {
                notWantedIngredientsRVAdapter?.setIngredients(editIngredientsViewModel.getNotWantedIngredients().value!!)
                notWantedIngredientsRVAdapter?.notifyDataSetChanged()
            }
        })
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