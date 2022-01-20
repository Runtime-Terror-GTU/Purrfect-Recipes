package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.*
import com.pr.purrfectrecipes.Adapters.ModeratorRecipeRVAdapter
import com.pr.purrfectrecipes.Connectors.ModDeleteRecipeOnClickListener

class RecipesModeratorChildfragment: Fragment(R.layout.childfragment_moderator_recipes),
        ModDeleteRecipeOnClickListener
{
    private val viewModel:RecipesModeratorViewModel by activityViewModels()
    private var modRVAdapter: ModeratorRecipeRVAdapter?=null
    private val sortViewModel: SortViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainLayout=view.findViewById<LinearLayout>(R.id.mainLayout)
        val progressBar=view.findViewById<ProgressBar>(R.id.loadingBar)

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                viewModel.setShownRecipe(null)
                sortViewModel.resetModeratorSort()
                filterViewModel.resetModeratorFilter()
                viewModel.resetRecipeArray()
                sortViewModel.setModeratorSortId(-1)
                super.onViewCreated(view, savedInstanceState)
            }
        })
        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null){
                modRVAdapter?.setRecipeList(viewModel.getRecipes().value!!)
                modRVAdapter?.notifyDataSetChanged()

                mainLayout.visibility=View.VISIBLE
                progressBar.visibility=View.GONE

                if(viewModel.change==true)
                {
                    viewModel.change=false
                    redoOperations(false)
                }

            }
        })
        setRVAdapter()

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
            viewModel.setSort(true)
            Hawk.put(Constants.SORT_DIRECTION, Constants.MODERATOR_TO_SORT)
        }

        val filterButton=view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener{
            viewModel.setFilter(true)
            Hawk.put(Constants.FILTER_DIRECTION, Constants.MODERATOR_TO_FILTER)
        }

        sortViewModel.getDiffModeratorSort().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        sortViewModel.getPopModeratorSort().observe(viewLifecycleOwner,{
            redoOperations(true)
        })

        filterViewModel.getChosenTagsModerator().observe(viewLifecycleOwner, {
            redoOperations(true)
        })

        filterViewModel.getChosenDifficultiesModerator().observe(viewLifecycleOwner, {
            redoOperations(true)
        })


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
        if(filterViewModel.getChosenTagsModerator().value!= null && filterViewModel.getChosenTagsModerator().value!!.size!=0)
            viewModel.applyTagFilters(filterViewModel.getChosenTagsModerator().value!!)
        if(filterViewModel.getChosenDifficultiesModerator().value!= null && filterViewModel.getChosenDifficultiesModerator().value!!.size!=0)
            viewModel.applyDifficultyFilters(filterViewModel.getChosenDifficultiesModerator().value!!)

        //Redo sort
        if(sortViewModel.getDiffModeratorSort().value!=null && sortViewModel.getDiffModeratorSort().value== SortMethods.difMintoMax)
            viewModel.sortDiffMin()
        else if(sortViewModel.getDiffModeratorSort().value!=null && sortViewModel.getDiffModeratorSort().value== SortMethods.difMaxtoMin)
            viewModel.sortDiffMax()
        if(sortViewModel.getPopModeratorSort().value!=null && sortViewModel.getPopModeratorSort().value== SortMethods.popMaxtoMin)
            viewModel.sortPopMax()
        else if(sortViewModel.getPopModeratorSort().value!=null && sortViewModel.getPopModeratorSort().value== SortMethods.popMintoMax)
            viewModel.sortPopMin()
    }

    override fun onRecipeClick(recipeId: String) {
        viewModel.setShownRecipe(recipeId)
    }

}