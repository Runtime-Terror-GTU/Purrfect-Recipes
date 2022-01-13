package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Adapters.RecipesRVAdapter2
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener2
import com.example.purrfectrecipes.FilterViewModel
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.SortViewModel

class PurrfectedrecipesProfileChildfragment: Fragment(R.layout.childfragment_profile_purrfectedrecipes),
    RecipeOnClickListener
{
    private val viewModel: PurrfectedrecipesProfileViewModel by activityViewModels()

    private var recipesRVAdapter: HomePageRVAdapter?=null

    private val sortViewModel: SortViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by activityViewModels()

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

        setRVAdapter()
        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null) {
                if(viewModel.user!=null) {
                    recipesRVAdapter?.setUser(viewModel.user!!)
                }

                recipesRVAdapter?.setRecipes(viewModel.getRecipes().value!!)
                recipesRVAdapter?.notifyDataSetChanged()

                if(viewModel.change==true)
                {
                    viewModel.change=false
                    //redoOperations(false)
                }
            }
        })
    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = GridLayoutManager(requireContext(), 2)
        recipesRVAdapter = HomePageRVAdapter(requireContext(), this)
        recipesGridView?.adapter = recipesRVAdapter
    }

    override fun onRecipeClick(recipeId: String) {
        viewModel.setShownRecipe(recipeId)
    }

    override fun onPurrfect(recipeId: String, recipeLikes: Int) {
        viewModel.change=true
        viewModel.purrfectRecipe(recipeId, recipeLikes)
    }

    override fun unPurrfect(recipeId: String, recipeLikes: Int) {
        viewModel.change=true
        viewModel.unPurrfectRecipe(recipeId, recipeLikes)
    }

}