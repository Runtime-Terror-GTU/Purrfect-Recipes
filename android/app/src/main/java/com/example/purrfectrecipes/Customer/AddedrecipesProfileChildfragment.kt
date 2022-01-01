package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Adapters.RecipesRVAdapter2
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener2
import com.example.purrfectrecipes.Moderator.ModeratorFragmentViewModel
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.SortMethods
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddedrecipesProfileChildfragment: Fragment(R.layout.childfragment_profile_addedrecipes), RecipeOnClickListener2
{
    private var recipesRVAdapter:RecipesRVAdapter2?=null

    private val viewModel: AddedrecipesProfileViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                viewModel.setShownRecipe(null)
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
                    redoOperations(false)
                }
            }
        })

    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recipesRVAdapter = RecipesRVAdapter2(requireContext(), this)
        recipesGridView?.adapter = recipesRVAdapter
    }

    fun redoOperations(reset:Boolean)
    {
        /*if(reset)
            viewModel.resetRecipeArray()

        val byName=view?.findViewById<RadioButton>(R.id.byName)
        val byUsername=view?.findViewById<RadioButton>(R.id.byUsername)
        val searchText=view?.findViewById<EditText>(R.id.searchText)
        //Redo search
        if(searchText?.text.isNullOrEmpty()){ }
        else if(byName!!.isChecked)
            viewModel.searchByName(searchText?.text.toString())
        else if(byUsername!!.isChecked)
            viewModel.searchByUsername(searchText?.text.toString())

        //Redo filter
        if(filterViewModel.getChosenTagsWhat().value!= null && filterViewModel.getChosenTagsWhat().value!!.size!=0)
            viewModel.applyTagFilters(filterViewModel.getChosenTagsWhat().value!!)
        if(filterViewModel.getChosenDifficultiesWhat().value!= null && filterViewModel.getChosenDifficultiesWhat().value!!.size!=0)
            viewModel.applyDifficultyFilters(filterViewModel.getChosenDifficultiesWhat().value!!)

        //Redo sort
        if(sortViewModel.getDiffWhatSort().value!=null && sortViewModel.getDiffWhatSort().value== SortMethods.difMintoMax)
            viewModel.sortDiffMin()
        else if(sortViewModel.getDiffWhatSort().value!=null && sortViewModel.getDiffWhatSort().value== SortMethods.difMaxtoMin)
            viewModel.sortDiffMax()
        if(sortViewModel.getPopWhatSort().value!=null && sortViewModel.getPopWhatSort().value== SortMethods.popMaxtoMin)
            viewModel.sortPopMax()
        else if(sortViewModel.getPopWhatSort().value!=null && sortViewModel.getPopWhatSort().value== SortMethods.popMintoMax)
            viewModel.sortPopMin()*/
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

    override fun onDelete(recipeId: String) {
        TODO("Not yet implemented")
    }

}