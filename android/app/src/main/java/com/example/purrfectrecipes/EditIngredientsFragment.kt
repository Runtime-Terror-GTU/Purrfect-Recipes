package com.example.purrfectrecipes

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.IngredientsRVAdapter
import com.example.purrfectrecipes.Adapters.TagsRVAdapter

class EditIngredientsFragment : Fragment(R.layout.fragment_edit_ingredients) {

    private val viewModel:EditIngredientsViewModel by activityViewModels()
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

    }

    fun setRVAdapter()
    {
        val recipeTypes = view?.findViewById<RecyclerView>(R.id.ingredientsList)
        recipeTypes?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ingredientsRVAdapter = IngredientsRVAdapter(requireContext())
        recipeTypes?.adapter = ingredientsRVAdapter
    }
}