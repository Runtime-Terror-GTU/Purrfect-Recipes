package com.pr.purrfectrecipes

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr.purrfectrecipes.Adapters.*
import com.pr.purrfectrecipes.Connectors.CommentChangeListener
import com.pr.purrfectrecipes.Customer.AddedrecipesProfileViewModel
import com.pr.purrfectrecipes.Customer.PurrfectedrecipesProfileViewModel
import com.pr.purrfectrecipes.Customer.RecipesHomeViewModel
import com.pr.purrfectrecipes.Customer.WhatresHomeViewModel
import com.pr.purrfectrecipes.User.CustomerStatus
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Moderator.RecipesModeratorViewModel

class RecipeFragment : Fragment(R.layout.fragment_recipe), CommentChangeListener
{
    private var commentsRVAdapter:CommentsRVAdapter?=null
    private var stepsRVAdapter:RecipeStepsRVAdapter?=null
    private var tagsRVAdapter:RecipeTagsRVAdapter?=null

    private val viewModel:RecipeViewModel by activityViewModels()

    private val recipesHomeViewModel:RecipesHomeViewModel by activityViewModels()
    private val whatresViewModel:WhatresHomeViewModel by activityViewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
    private val purrfectedRecipesViewModel:PurrfectedrecipesProfileViewModel by activityViewModels()
    private val moderatorRecipesViewModel: RecipesModeratorViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeName=view.findViewById<TextView>(R.id.recipeName)
        val recipeLikes=view.findViewById<TextView>(R.id.recipeLikes)
        val recipePic=view.findViewById<ImageView>(R.id.recipePic)
        val recipeIngredientsOverview=view.findViewById<TextView>(R.id.ingredientsOverview)

        val ownerName=view.findViewById<TextView>(R.id.ownerName)
        val ownerPic=view.findViewById<ImageView>(R.id.ownerPic)

        val deleteRecipeButton=view.findViewById<ImageView>(R.id.deleteRecipeButton)
        val purrfectButton=view.findViewById<CardView>(R.id.purrfectButton)
        val premiumSymbol=view.findViewById<ImageView>(R.id.premiumSymbol)
        val addCommentLayout=view.findViewById<LinearLayout>(R.id.addCommentLayout)

        val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
        val mainLayout=view.findViewById<LinearLayout>(R.id.mainLayout)

        setRVAdapter()

        recipesHomeViewModel.getShownRecipe().observe(viewLifecycleOwner,{
            if(recipesHomeViewModel.getShownRecipe().value!=null)
                viewModel.setRecipe(recipesHomeViewModel.getShownRecipe().value!!)
        })

        whatresViewModel.getShownRecipe().observe(viewLifecycleOwner,{
            if(whatresViewModel.getShownRecipe().value!=null)
                viewModel.setRecipe(whatresViewModel.getShownRecipe().value!!)
        })

        addedRecipesViewModel.getShownRecipe().observe(viewLifecycleOwner,{
            if( addedRecipesViewModel.getShownRecipe().value!=null)
                viewModel.setRecipe( addedRecipesViewModel.getShownRecipe().value!!)
        })

        purrfectedRecipesViewModel.getShownRecipe().observe(viewLifecycleOwner,{
            if( purrfectedRecipesViewModel.getShownRecipe().value!=null)
                viewModel.setRecipe( purrfectedRecipesViewModel.getShownRecipe().value!!)
        })

        moderatorRecipesViewModel.getShownRecipe().observe(viewLifecycleOwner,{
            if( moderatorRecipesViewModel.getShownRecipe().value!=null)
                viewModel.setRecipe( moderatorRecipesViewModel.getShownRecipe().value!!)
        })

        viewModel.getRecipe().observe(viewLifecycleOwner,{
            if(viewModel.getRecipe().value==null)
            {
                loadingBar.visibility=View.VISIBLE
                mainLayout.visibility=View.GONE
            }
            else
            {
                recipeName.text=viewModel.getRecipe().value!!.recipeName
                recipeLikes.text=viewModel.getRecipe().value!!.recipeLikes.toString()
                recipeIngredientsOverview.text=viewModel.getRecipe().value!!.recipeIngredientsOverview
                recipeIngredientsOverview.text=recipeIngredientsOverview.text!!.toString().replace("\\n", "\n")
                Glide.with(requireContext())
                    .load(viewModel.getRecipe().value?.recipePictureURL)
                    .into(recipePic)
                ownerName.text=viewModel.recipeOwner?.getUsername()
                Glide.with(requireContext())
                    .load(viewModel.recipeOwner?.getUserPic())
                    .into(ownerPic)

                val tagsTemp=ArrayList<String>()
                tagsTemp.add(viewModel.getRecipe().value!!.recipeDifficulty)
                tagsTemp.addAll(viewModel.getRecipe().value!!.getRecipeTags())
                tagsRVAdapter?.setTags(tagsTemp)
                tagsRVAdapter?.notifyDataSetChanged()
                stepsRVAdapter?.setSteps(viewModel.getRecipe().value!!.getRecipeStages())
                stepsRVAdapter?.notifyDataSetChanged()

                if(viewModel.recipeOwner?.getUserStatus()== CustomerStatus.PREMIUM)
                    premiumSymbol.visibility=View.VISIBLE
                else
                    premiumSymbol.visibility=View.GONE

                if(viewModel.recipeOwner?.getUserID()==Hawk.get(Constants.LOGGEDIN_USERID) || Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.MODERATOR)
                    deleteRecipeButton.visibility=View.VISIBLE
                else
                    deleteRecipeButton.visibility=View.GONE

                if(Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.UNVERIFIED || Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.VERIFIED
                    || Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.PREMIUM)
                    addCommentLayout.visibility=View.VISIBLE
                else
                    addCommentLayout.visibility=View.GONE

                if(viewModel.user!=null && viewModel.user!!.isPurrfectedRecipe(viewModel.getRecipe().value!!.getRecipeID()))
                    purrfectButton.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary))

                loadingBar.visibility=View.GONE
                mainLayout.visibility=View.VISIBLE
            }
        })

        viewModel.getComments().observe(viewLifecycleOwner,{
            if(viewModel.getComments().value!=null)
            {
                commentsRVAdapter?.setComments(viewModel.getComments().value!!)
                commentsRVAdapter?.notifyDataSetChanged()
            }
        })

        val addedComment=view.findViewById<EditText>(R.id.addedComment)
        val addCommentButton=view.findViewById<Button>(R.id.addCommentButton)
        addCommentButton.setOnClickListener {
            if(addedComment.text.isNullOrEmpty())
                Toast.makeText(requireContext(), "Please enter a comment first.", Toast.LENGTH_SHORT).show()
            else {
                viewModel.addComment(addedComment.text.toString())
                addedComment.setText("")
                addedComment.clearFocus()
            }
        }

        deleteRecipeButton.setOnClickListener {
            viewModel.deleteRecipe()

            if(recipesHomeViewModel.getShownRecipe().value!=null)
                recipesHomeViewModel.setShownRecipe(null)
            if(whatresViewModel.getShownRecipe().value!=null)
                whatresViewModel.setShownRecipe(null)
            if(addedRecipesViewModel.getShownRecipe().value!=null)
                addedRecipesViewModel.setShownRecipe(null)
            if(purrfectedRecipesViewModel.getShownRecipe().value!=null)
                purrfectedRecipesViewModel.setShownRecipe(null)
            if(moderatorRecipesViewModel.getShownRecipe().value!=null)
                moderatorRecipesViewModel.setShownRecipe(null)
            viewModel.resetRecipe()
        }

        purrfectButton.setOnClickListener {
            if(viewModel.user!=null && viewModel.user!!.getUserStatus()!=CustomerStatus.MODERATOR && (!viewModel.user!!.isPurrfectedRecipe(viewModel.getRecipe().value!!.getRecipeID())))
            {
                viewModel.purrfectRecipe()
                purrfectButton.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.secondary))
            }
            else if (viewModel.user!=null && viewModel.user!!.getUserStatus()!=CustomerStatus.MODERATOR)
            {
                viewModel.unPurrfectRecipe()
                purrfectButton.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
        }


    }

    fun setRVAdapter()
    {
        val tagsRV = view?.findViewById<RecyclerView>(R.id.tagsRV)
        tagsRV?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        tagsRVAdapter = RecipeTagsRVAdapter(requireContext())
        tagsRV?.adapter = tagsRVAdapter

        val recipeStepsRV = view?.findViewById<RecyclerView>(R.id.recipeStepsRV)
        recipeStepsRV?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        stepsRVAdapter = RecipeStepsRVAdapter(requireContext())
        recipeStepsRV?.adapter = stepsRVAdapter

        val recipeComments = view?.findViewById<RecyclerView>(R.id.recipeComments)
        recipeComments?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        commentsRVAdapter = CommentsRVAdapter(requireContext(), this)
        recipeComments?.adapter = commentsRVAdapter
    }

    override fun onDeleteComment(commentId: String) {
        viewModel.deleteComment(commentId)
    }
}