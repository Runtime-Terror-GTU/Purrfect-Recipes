package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.*
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Adapters.RecipesRVAdapter2
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener2
import com.example.purrfectrecipes.Moderator.ModeratorFragmentViewModel
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class AddedrecipesProfileChildfragment: Fragment(R.layout.childfragment_profile_addedrecipes), RecipeOnClickListener2
{
    private var recipesRVAdapter:RecipesRVAdapter2?=null

    private val viewModel: AddedrecipesProfileViewModel by activityViewModels()

    private val sortViewModel: SortViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                viewModel.setShownRecipe(null)
                sortViewModel.resetAddedSort()
                filterViewModel.resetAddedFilter()
                viewModel.resetRecipeArray()
                sortViewModel.setAddedSortId(-1)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        val verifiedLayout=view.findViewById<LinearLayout>(R.id.verifiedLayout)
        val unverifiedLayout=view.findViewById<RelativeLayout>(R.id.unverifiedLayout)
        if(Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.VERIFIED || Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.PREMIUM)
        {
            unverifiedLayout.visibility=View.GONE
            verifiedLayout.visibility=View.VISIBLE
        }
        else
        {
            unverifiedLayout.visibility=View.VISIBLE
            verifiedLayout.visibility=View.GONE
        }

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

        val searchDoneButton=view.findViewById<Button>(R.id.searchDoneButton)
        val searchCancelButton=view.findViewById<ImageView>(R.id.cancelSearchButton)
        val searchText=view.findViewById<EditText>(R.id.searchText)
        searchDoneButton.setOnClickListener{
            redoOperations(true)
        }
        searchCancelButton.setOnClickListener {
            searchText.setText("")
            searchText.clearFocus()
            redoOperations(true)
        }

        val sortButton=view.findViewById<Button>(R.id.sortButton)
        sortButton.setOnClickListener {
            Hawk.put(Constants.SORT_DIRECTION, Constants.ADDED_TO_SORT)
            viewModel.setSort(true)
        }

        val filterButton=view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener{
            Hawk.put(Constants.FILTER_DIRECTION, Constants.ADDED_TO_FILTER)
            viewModel.setFilter(true)
        }

        sortViewModel.getDiffAddedSort().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        sortViewModel.getPopAddedSort().observe(viewLifecycleOwner,{
            redoOperations(true)
        })

        filterViewModel.getChosenTagsAdded().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        filterViewModel.getChosenDifficultiesAdded().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        val goToGetVerifiedButton=view.findViewById<LinearLayout>(R.id.goToGetVerifiedButton)
        goToGetVerifiedButton.setOnClickListener {
            viewModel.setGetVerified(true)
        }

        val addRecipeButton=view.findViewById<Button>(R.id.addRecipeButton)
        addRecipeButton.setOnClickListener {
            viewModel.setEditRecipe(true)
        }

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
        if(reset)
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
        if(filterViewModel.getChosenTagsAdded().value!= null && filterViewModel.getChosenTagsAdded().value!!.size!=0)
            viewModel.applyTagFilters(filterViewModel.getChosenTagsAdded().value!!)
        if(filterViewModel.getChosenDifficultiesAdded().value!= null && filterViewModel.getChosenDifficultiesAdded().value!!.size!=0)
            viewModel.applyDifficultyFilters(filterViewModel.getChosenDifficultiesAdded().value!!)

        //Redo sort
        if(sortViewModel.getDiffAddedSort().value!=null && sortViewModel.getDiffAddedSort().value== SortMethods.difMintoMax)
            viewModel.sortDiffMin()
        else if(sortViewModel.getDiffAddedSort().value!=null && sortViewModel.getDiffAddedSort().value== SortMethods.difMaxtoMin)
            viewModel.sortDiffMax()
        if(sortViewModel.getPopAddedSort().value!=null && sortViewModel.getPopAddedSort().value== SortMethods.popMaxtoMin)
            viewModel.sortPopMax()
        else if(sortViewModel.getPopAddedSort().value!=null && sortViewModel.getPopAddedSort().value== SortMethods.popMintoMax)
            viewModel.sortPopMin()
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

    override fun onDelete(recipe: Recipe) {
        viewModel.deleteRecipe(recipe)
    }

}