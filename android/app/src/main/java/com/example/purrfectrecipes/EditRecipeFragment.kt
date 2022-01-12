package com.example.purrfectrecipes

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purrfectrecipes.Adapters.ChosenIngredientsRVAdapter
import com.example.purrfectrecipes.Adapters.RecipeNewStepsRVAdapter
import com.example.purrfectrecipes.Adapters.RecipeStepsRVAdapter
import com.example.purrfectrecipes.Customer.AddedrecipesProfileViewModel
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri


class EditRecipeFragment : Fragment(R.layout.fragment_edit_recipe) {

    private val viewModel:EditRecipeViewModel by activityViewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
    private var recipeTagsRVAdapter: ChosenIngredientsRVAdapter?=null
    private var recipeIngredientsRVAdapter: ChosenIngredientsRVAdapter?=null
    private var recipeStepsRVAdapter: RecipeNewStepsRVAdapter?=null

    val PICK_IMAGE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedRecipesViewModel.getEditedRecipe().observe(viewLifecycleOwner,{
                viewModel.setRecipe(addedRecipesViewModel.getEditedRecipe().value)
        })

        setRVAdapter()

        val recipePic=view.findViewById<ImageView>(R.id.recipePic)
        val recipeName=view.findViewById<EditText>(R.id.recipeName)
        val recipeIngredientDetails=view.findViewById<EditText>(R.id.recipeIngredientDetails)

        viewModel.getRecipeSteps().observe(viewLifecycleOwner,{
                recipeStepsRVAdapter?.setSteps(viewModel.getRecipeSteps().value!!)
                recipeStepsRVAdapter?.notifyDataSetChanged()
        })

        viewModel.getRecipe().observe(viewLifecycleOwner,{
            if(viewModel.getRecipe().value!=null)
            {
                Glide.with(requireContext())
                    .load(viewModel.getRecipe().value?.recipePictureURL)
                    .into(recipePic)
                recipeName.setText(viewModel.getRecipe().value?.recipeName)

                recipeIngredientDetails.setText(viewModel.getRecipe().value?.recipeIngredientsOverview)
                recipeIngredientDetails.setText(recipeIngredientDetails.text!!.toString().replace("\\n", "\n"))

                recipeTagsRVAdapter?.setIngredients(viewModel.getRecipe().value?.getRecipeTags()!!)
                recipeTagsRVAdapter?.notifyDataSetChanged()
                recipeIngredientsRVAdapter?.setIngredients(viewModel.getRecipe().value?.getRecipeIngredients()!!)
                recipeIngredientsRVAdapter?.notifyDataSetChanged()
                recipeStepsRVAdapter?.setSteps(viewModel.getRecipe().value?.getRecipeStages()!!)
                recipeStepsRVAdapter?.notifyDataSetChanged()

            }
            else
            {
                recipePic.setBackgroundResource(R.drawable.no_photo)

                recipeName.setText("")
                recipeIngredientDetails.setText("")

                recipeTagsRVAdapter?.setIngredients(ArrayList())
                recipeTagsRVAdapter?.notifyDataSetChanged()
                recipeIngredientsRVAdapter?.setIngredients(ArrayList())
                recipeIngredientsRVAdapter?.notifyDataSetChanged()
                recipeStepsRVAdapter?.setSteps(ArrayList())
                recipeStepsRVAdapter?.notifyDataSetChanged()
            }
        })

        val addNewStepButton=view.findViewById<LinearLayout>(R.id.addStep)
        val newStep=view.findViewById<EditText>(R.id.newStep)
        addNewStepButton.setOnClickListener {
            if(newStep.text.isNullOrEmpty())
                Toast.makeText(requireContext(), "Please enter the step information first.", Toast.LENGTH_SHORT).show()
            else
            {
                viewModel.addStep(newStep.text.toString())
                newStep.clearFocus()
                newStep.setText("")
            }
        }

        val changeRecipePicButton=view.findViewById<TextView>(R.id.changeRecipePic)
        changeRecipePicButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        val addTag=view.findViewById<Button>(R.id.addTag)
        addTag.setOnClickListener {
            
        }
        val addIngredient=view.findViewById<Button>(R.id.addIngredient)
        addIngredient.setOnClickListener {

        }

        val cancelEditButton=view.findViewById<ImageView>(R.id.cancelEdit)
        cancelEditButton.setOnClickListener {
            addedRecipesViewModel.setEditRecipe(false)
        }

        val editRecipeButton=view.findViewById<LinearLayout>(R.id.editRecipe)
        editRecipeButton.setOnClickListener {
            if(recipeName.text.isNullOrEmpty())
                Toast.makeText(requireContext(), "Please enter recipe name first.", Toast.LENGTH_SHORT).show()
            else if(recipeIngredientDetails.text.isNullOrEmpty())
                Toast.makeText(requireContext(), "Please enter recipe ingredient details first.", Toast.LENGTH_SHORT).show()
            else if(viewModel.getRecipe().value!=null && viewModel.getRecipeIngredients().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe ingredients first.", Toast.LENGTH_SHORT).show()
            else if(viewModel.getRecipe().value!=null && viewModel.getRecipeTags().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe tags first.", Toast.LENGTH_SHORT).show()
            else if(viewModel.getRecipe().value!=null && viewModel.getRecipeSteps().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe steps first.", Toast.LENGTH_SHORT).show()
            else
            {

            }
        }
    }

    fun setRVAdapter()
    {
        val recipeTags = view?.findViewById<RecyclerView>(R.id.recipeTags)
        recipeTags?.layoutManager = GridLayoutManager(requireContext(), 3)
        recipeTagsRVAdapter = ChosenIngredientsRVAdapter(requireContext())
        recipeTags?.adapter =  recipeTagsRVAdapter

        val recipeIngredients = view?.findViewById<RecyclerView>(R.id.recipeIngredients)
        recipeIngredients?.layoutManager = GridLayoutManager(requireContext(), 3)
        recipeIngredientsRVAdapter = ChosenIngredientsRVAdapter(requireContext())
        recipeIngredients?.adapter =  recipeIngredientsRVAdapter

        val recipeStepsRV = view?.findViewById<RecyclerView>(R.id.recipePreparation)
        recipeStepsRV?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recipeStepsRVAdapter = RecipeNewStepsRVAdapter(requireContext())
        recipeStepsRV?.adapter = recipeStepsRVAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val recipePic=view?.findViewById<ImageView>(R.id.recipePic)
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.getData()
            recipePic?.setImageURI(selectedImageUri)
        }
    }
}