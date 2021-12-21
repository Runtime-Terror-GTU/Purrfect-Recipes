package com.example.purrfectrecipes

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.IngredientsRVAdapter
import com.example.purrfectrecipes.Adapters.TagsRVAdapter
import com.example.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.example.purrfectrecipes.Customer.WhatHomeViewModel

class EditIngredientsFragment : Fragment(R.layout.fragment_edit_ingredients), IngredientOnSelectedListener {

    private val viewModel:EditIngredientsViewModel by activityViewModels()
    private val whatHomeViewModel:WhatHomeViewModel by activityViewModels()
    private var ingredientsRVAdapter: IngredientsRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setRVAdapter()

        val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
        val recyclerView=view.findViewById<RecyclerView>(R.id.ingredientsList)

        viewModel.getIngredients().observe(viewLifecycleOwner,{
            if(viewModel.getIngredients().value!=null && viewModel.getIngredients().value!!.size!=0)
            {
                loadingBar.visibility=View.GONE
                recyclerView.visibility=View.VISIBLE
                ingredientsRVAdapter?.setIngredients(viewModel.getIngredients().value!!)
                ingredientsRVAdapter?.notifyDataSetChanged()
            }
        })

        val enterButton=view.findViewById<LinearLayout>(R.id.doneButton)
        val cancelButton=view.findViewById<ImageView>(R.id.cancelEditIngredients)

        whatHomeViewModel.getEditWanted().observe(viewLifecycleOwner,{
            if(whatHomeViewModel.getEditWanted().value!=null && whatHomeViewModel.getEditWanted().value==true)
            {
                cancelButton.setOnClickListener{
                    viewModel.tempIngredients.clear()
                    whatHomeViewModel.setEditWanted(false)
                }

                enterButton.setOnClickListener {
                    viewModel.setWantedIngredients(viewModel.tempIngredients)
                    viewModel.tempIngredients.clear()
                    whatHomeViewModel.setEditWanted(false)
                }

                viewModel.getWantedIngredients().observe(viewLifecycleOwner,{
                    if(viewModel.getWantedIngredients().value!=null && viewModel.getWantedIngredients().value!!.size!=0)
                    {
                        ingredientsRVAdapter?.setChosen(viewModel.getWantedIngredients().value!!)
                        ingredientsRVAdapter?.notifyDataSetChanged()
                    }
                })
            }
        })
        whatHomeViewModel.getEditNotWanted().observe(viewLifecycleOwner, {
            if(whatHomeViewModel.getEditNotWanted().value!=null && whatHomeViewModel.getEditNotWanted().value==true) {
                cancelButton.setOnClickListener {
                    viewModel.tempIngredients.clear()
                    whatHomeViewModel.setEditNotWanted(false)
                }

                enterButton.setOnClickListener {
                    viewModel.setNotWantedIngredients(viewModel.tempIngredients)
                    viewModel.tempIngredients.clear()
                    whatHomeViewModel.setEditNotWanted(false)
                }

                viewModel.getNotWantedIngredients().observe(viewLifecycleOwner, {
                    if (viewModel.getNotWantedIngredients().value != null && viewModel.getNotWantedIngredients().value!!.size != 0) {
                        ingredientsRVAdapter?.setChosen(viewModel.getNotWantedIngredients().value!!)
                        ingredientsRVAdapter?.notifyDataSetChanged()
                    }
                })
            }
        })

        val searchBar=view.findViewById<androidx.appcompat.widget.SearchView>(R.id.ingredientSearchBar)

        searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchBar.clearFocus()
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<String>()
                    for(ingredient in viewModel.getIngredients().value!!)
                        if(ingredient.startsWith(query.lowercase()))
                            tempArray.add(ingredient)
                    ingredientsRVAdapter?.setIngredients(tempArray)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    ingredientsRVAdapter?.setIngredients(viewModel.getIngredients().value!!)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false

            }
            override fun onQueryTextChange(query: String): Boolean {
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<String>()
                    for(ingredient in viewModel.getIngredients().value!!)
                        if(ingredient.startsWith(query.lowercase()))
                            tempArray.add(ingredient)
                    ingredientsRVAdapter?.setIngredients(tempArray)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    ingredientsRVAdapter?.setIngredients(viewModel.getIngredients().value!!)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false
            }
        })


    }



    fun setRVAdapter()
    {
        val recipeTypes = view?.findViewById<RecyclerView>(R.id.ingredientsList)
        recipeTypes?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ingredientsRVAdapter = IngredientsRVAdapter(requireContext(), this)
        recipeTypes?.adapter = ingredientsRVAdapter
    }

    override fun onSelectIngredient(selectedIngredient: String) {
        viewModel.tempIngredients.add(selectedIngredient)
    }

    override fun deselectIngredient(deSelectedIngredient: String) {
        viewModel.tempIngredients.remove(deSelectedIngredient)
    }
}