package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.*
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.orhanobut.hawk.Hawk

class WhatresHomeChildfragment: Fragment(R.layout.childfragment_home_whatres)
{
    val viewModel:WhatresHomeViewModel by activityViewModels()
    val whatHomeViewModel:WhatHomeViewModel by activityViewModels()

    private val sortViewModel: SortViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by activityViewModels()

    private var recipesRVAdapter: HomePageRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                sortViewModel.resetWhatSort()
                filterViewModel.resetWhatFilter()
                viewModel.resetRecipeArray()
                sortViewModel.setWhatSortId(-1)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        viewModel.getTotalRecipes().observe(viewLifecycleOwner,{
            if(viewModel.getTotalRecipes().value!=null)
                viewModel.setRecipes(whatHomeViewModel.getWantedIngredients().value!!, whatHomeViewModel.getNotWantedIngredients().value!!)
        })

        setRVAdapter()
        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null) {
                recipesRVAdapter?.setRecipes(viewModel.getRecipes().value!!)
                recipesRVAdapter?.notifyDataSetChanged()
            }
        })

        val searchDoneButton=view.findViewById<Button>(R.id.searchDoneButton)
        val searchCancelButton=view.findViewById<ImageView>(R.id.cancelSearchButton)
        val searchText=view.findViewById<EditText>(R.id.searchText)
        searchDoneButton.setOnClickListener{
            redoOperations()
        }
        searchCancelButton.setOnClickListener {
            searchText.setText("")
            searchText.clearFocus()
            redoOperations()
        }

        val sortButton=view.findViewById<Button>(R.id.sortButton)
        sortButton.setOnClickListener {
            viewModel.setSort(true)
            Hawk.put(Constants.SORT_DIRECTION, Constants.WHAT_TO_SORT)
        }

        val filterButton=view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener{
            viewModel.setFilter(true)
            Hawk.put(Constants.FILTER_DIRECTION, Constants.WHAT_TO_FILTER)
        }

        sortViewModel.getDiffWhatSort().observe(viewLifecycleOwner, {
            redoOperations()
        })

        sortViewModel.getPopWhatSort().observe(viewLifecycleOwner,{
            redoOperations()
        })

        filterViewModel.getChosenTagsWhat().observe(viewLifecycleOwner, {
            redoOperations()
        })

        filterViewModel.getChosenDifficultiesWhat().observe(viewLifecycleOwner, {
            redoOperations()
        })
    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = GridLayoutManager(requireActivity(), 2)
        recipesRVAdapter = HomePageRVAdapter(requireContext())
        recipesGridView?.adapter = recipesRVAdapter
    }

    fun redoOperations()
    {
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
            viewModel.sortPopMin()
    }

}