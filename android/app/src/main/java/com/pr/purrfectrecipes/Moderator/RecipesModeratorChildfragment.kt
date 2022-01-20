package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Adapters.ModeratorRecipeRVAdapter
import com.pr.purrfectrecipes.Connectors.ModDeleteRecipeOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.Recipe

class RecipesModeratorChildfragment: Fragment(R.layout.childfragment_moderator_recipes),
        ModDeleteRecipeOnClickListener
{
    private val viewModel:RecipesModeratorViewModel by activityViewModels()
    private var modRVAdapter: ModeratorRecipeRVAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainLayout=view.findViewById<LinearLayout>(R.id.mainLayout)
        val progressBar=view.findViewById<ProgressBar>(R.id.loadingBar)

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })
        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null){
                modRVAdapter?.setRecipeList(viewModel.getRecipes().value!!)
                modRVAdapter?.notifyDataSetChanged()

                mainLayout.visibility=View.VISIBLE
                progressBar.visibility=View.GONE

            }
        })
        setRVAdapter()
    }
    fun setRVAdapter()
    {
        val recipes = view?.findViewById<RecyclerView>(R.id.recipes)
        recipes?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        modRVAdapter = ModeratorRecipeRVAdapter(requireContext(),this)
        recipes?.adapter = modRVAdapter
    }
    override fun onDeleteClick(recipe: Recipe){
        viewModel.deleteRecipe(recipe)
    }

}