package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Adapters.IngredientRequestsPageRVAdapter
import com.pr.purrfectrecipes.Connectors.RequestOnClickListener
import com.pr.purrfectrecipes.R

class RequestsModeratorChildfragment: Fragment(R.layout.childfragment_moderator_requests),
    RequestOnClickListener

{
    private val viewModel: RequestsModeratorViewModel by activityViewModels()
    private var ingredientRequestsRVAdapter: IngredientRequestsPageRVAdapter?=null
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
        viewModel.getSuggestions().observe(viewLifecycleOwner, {
            if(viewModel.getSuggestions().value!=null){
                ingredientRequestsRVAdapter?.setSuggestions(viewModel.getSuggestions().value!!)
                ingredientRequestsRVAdapter?.notifyDataSetChanged()
            }
        })
        setRVAdapter()
    }
    fun setRVAdapter()
    {
        val ingredients = view?.findViewById<RecyclerView>(R.id.ingredients)
        ingredients?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        ingredientRequestsRVAdapter = IngredientRequestsPageRVAdapter(requireContext(),this)
        ingredients?.adapter = ingredientRequestsRVAdapter
    }

    override fun onApproveClick(requestID:String){
        viewModel.suggestionApprove(requestID,requireActivity())
    }
    override fun onDenyClick(requestID:String){
        viewModel.suggestionDeny(requestID,requireActivity())
    }


}