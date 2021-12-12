package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.R

class RecipesHomeChildfragment: Fragment(R.layout.childfragment_home_recipes)
{
    private val viewModel: RecipesHomeViewModel by viewModels()
    private var recipesRVAdapter:HomePageRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getView().value!=null)
            super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
        else
        {
            viewModel.setView(view)
            super.onViewCreated(view, savedInstanceState)
        }
        setRVAdapter()

        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null) {
                recipesRVAdapter?.setRecipes(viewModel.getRecipes().value!!)
                recipesRVAdapter?.notifyDataSetChanged()

                val homePage=view.findViewById<LinearLayout>(R.id.homePage)
                val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
                loadingBar.visibility=View.GONE
                homePage.visibility=View.VISIBLE
            }
        })

        viewModel.getRecipeOfTheDay().observe(viewLifecycleOwner,{
            if(viewModel.getRecipeOfTheDay().value!=null)
            {
                val recipeName=view.findViewById<TextView>(R.id.dayRecipeName)
                val recipeOwner=view.findViewById<TextView>(R.id.dayRecipeOwner)
                val recipeDifficulty=view.findViewById<TextView>(R.id.dayRecipeDifficulty)
                val recipeType=view.findViewById<TextView>(R.id.dayRecipeType)
                val recipeLikes=view.findViewById<TextView>(R.id.dayRecipeLikes)
                recipeName.text=viewModel.getRecipeOfTheDay().value?.recipeName
                recipeOwner.text="by "+viewModel.getRecipeOfTheDay().value?.recipeOwner
                recipeDifficulty.text=viewModel.getRecipeOfTheDay().value?.recipeDifficulty
                recipeType.text="Meal"
                recipeLikes.text=viewModel.getRecipeOfTheDay().value?.recipeLikes.toString()
            }
        })
    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = GridLayoutManager(requireActivity(), 2)
        recipesRVAdapter = HomePageRVAdapter(requireContext())
        recipesGridView?.adapter = recipesRVAdapter
    }

}