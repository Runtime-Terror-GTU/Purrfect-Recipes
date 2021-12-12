package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
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