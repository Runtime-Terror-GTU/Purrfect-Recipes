package com.pr.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.*
import com.pr.purrfectrecipes.Adapters.HomePageRVAdapter
import com.pr.purrfectrecipes.Connectors.RecipeOnClickListener
import com.orhanobut.hawk.Hawk

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
                viewModel.setShownRecipe(null)
                sortViewModel.resetPurrfectedSort()
                filterViewModel.resetPurrfectedFilter()
                viewModel.resetRecipeArray()
                sortViewModel.setPurrfectedSortId(-1)
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
            Hawk.put(Constants.SORT_DIRECTION, Constants.PURRFECTED_TO_SORT)
            viewModel.setSort(true)
        }

        val filterButton=view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener{
            Hawk.put(Constants.FILTER_DIRECTION, Constants.PURRFECTED_TO_FILTER)
            viewModel.setFilter(true)
        }

        sortViewModel.getDiffPurrfectedSort().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        sortViewModel.getPopPurrfectedSort().observe(viewLifecycleOwner,{
            redoOperations(true)
        })

        filterViewModel.getChosenTagsPurrfected().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        filterViewModel.getChosenDifficultiesPurrfected().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = GridLayoutManager(requireContext(), 2)
        recipesRVAdapter = HomePageRVAdapter(requireContext(), this)
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
        if(filterViewModel.getChosenTagsPurrfected().value!= null && filterViewModel.getChosenTagsPurrfected().value!!.size!=0)
            viewModel.applyTagFilters(filterViewModel.getChosenTagsPurrfected().value!!)
        if(filterViewModel.getChosenDifficultiesPurrfected().value!= null && filterViewModel.getChosenDifficultiesPurrfected().value!!.size!=0)
            viewModel.applyDifficultyFilters(filterViewModel.getChosenDifficultiesPurrfected().value!!)

        //Redo sort
        if(sortViewModel.getDiffPurrfectedSort().value!=null && sortViewModel.getDiffPurrfectedSort().value== SortMethods.difMintoMax)
            viewModel.sortDiffMin()
        else if(sortViewModel.getDiffPurrfectedSort().value!=null && sortViewModel.getDiffPurrfectedSort().value== SortMethods.difMaxtoMin)
            viewModel.sortDiffMax()
        if(sortViewModel.getPopPurrfectedSort().value!=null && sortViewModel.getPopPurrfectedSort().value== SortMethods.popMaxtoMin)
            viewModel.sortPopMax()
        else if(sortViewModel.getPopPurrfectedSort().value!=null && sortViewModel.getPopPurrfectedSort().value== SortMethods.popMintoMax)
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

}