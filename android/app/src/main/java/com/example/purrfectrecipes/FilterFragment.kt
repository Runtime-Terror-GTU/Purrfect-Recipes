package com.example.purrfectrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Adapters.TagsRVAdapter
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val homeViewModel: RecipesHomeViewModel by activityViewModels()
    private val viewModel:FilterViewModel by activityViewModels()
    private var tagsRVAdapter: TagsRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRVAdapter()

        val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
        val mainPart=view.findViewById<LinearLayout>(R.id.mainPart)

        viewModel.getTags().observe(viewLifecycleOwner,{
            if(viewModel.getTags().value!=null) {
                tagsRVAdapter?.setTags(viewModel.getTags().value!!)
                tagsRVAdapter?.notifyDataSetChanged()
                loadingBar.visibility=View.GONE
                mainPart.visibility=View.VISIBLE
            }
        })

        val cancelButton=view.findViewById<ImageView>(R.id.cancelFilter)
        val enterButton=view.findViewById<LinearLayout>(R.id.enterFilterButton)

        cancelButton.setOnClickListener {

            homeViewModel.setFilter(false)
        }

        enterButton.setOnClickListener {
                homeViewModel.setFilter(false)
        }
    }

    fun setRVAdapter()
    {
        val recipeTypes = view?.findViewById<RecyclerView>(R.id.recipeTypes)
        recipeTypes?.layoutManager = GridLayoutManager(requireActivity(), 3)
        tagsRVAdapter = TagsRVAdapter(requireContext())
        recipeTypes?.adapter = tagsRVAdapter
    }
}