package com.pr.purrfectrecipes

import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr.purrfectrecipes.Customer.AddedrecipesProfileViewModel
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import com.pr.purrfectrecipes.Adapters.*
import com.pr.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.pr.purrfectrecipes.Connectors.StepDeletedListener
import com.pr.purrfectrecipes.Connectors.TagOnSelectedListener
import com.orhanobut.hawk.Hawk
import java.util.*
import kotlin.collections.ArrayList


class EditRecipeFragment : Fragment(R.layout.fragment_edit_recipe), TagOnSelectedListener,
    IngredientOnSelectedListener,StepDeletedListener {

    private val viewModel:EditRecipeViewModel by activityViewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
    private var recipeTagsRVAdapter: ChosenIngredientsRVAdapter?=null
    private var recipeIngredientsRVAdapter: ChosenIngredientsRVAdapter?=null
    private var recipeStepsRVAdapter: RecipeNewStepsRVAdapter?=null

    val PICK_IMAGE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addedRecipesViewModel.getEditedRecipe().observe(viewLifecycleOwner,{
            viewModel.newRecipePicUri=null
            viewModel.setRecipe(addedRecipesViewModel.getEditedRecipe().value)
        })

        setRVAdapter()

        val recipePic=view.findViewById<ImageView>(R.id.recipePic)
        val recipeName=view.findViewById<EditText>(R.id.recipeName)
        val recipeIngredientDetails=view.findViewById<EditText>(R.id.recipeIngredientDetails)
        val difficultyOptions=view.findViewById<RadioGroup>(R.id.difficultyOptions)

        viewModel.getRecipeSteps().observe(viewLifecycleOwner,{
            recipeStepsRVAdapter?.setSteps(viewModel.getRecipeSteps().value!!)
            recipeStepsRVAdapter?.notifyDataSetChanged()
        })
        viewModel.getRecipeTags().observe(viewLifecycleOwner,{
            recipeTagsRVAdapter?.setIngredients(viewModel.getRecipeTags().value!!)
            recipeTagsRVAdapter?.notifyDataSetChanged()
        })
        viewModel.getRecipeIngredients().observe(viewLifecycleOwner,{
            recipeIngredientsRVAdapter?.setIngredients(viewModel.getRecipeIngredients().value!!)
            recipeIngredientsRVAdapter?.notifyDataSetChanged()
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

                if(viewModel.getRecipe().value!!.recipeDifficulty=="Easy")
                    difficultyOptions.check(R.id.easyOption)
                else if(viewModel.getRecipe().value!!.recipeDifficulty=="Medium")
                    difficultyOptions.check(R.id.mediumOption)
                else if(viewModel.getRecipe().value!!.recipeDifficulty=="Hard")
                    difficultyOptions.check(R.id.hardOption)

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
                difficultyOptions.clearCheck()

                viewModel.resetInfo()
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
        recipePic.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }

        val addTag=view.findViewById<Button>(R.id.addTag)
        addTag.setOnClickListener {
            addTags()
        }
        val addIngredient=view.findViewById<Button>(R.id.addIngredient)
        addIngredient.setOnClickListener {
            addIngredients()
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
            else if(viewModel.getRecipe().value==null && viewModel.getRecipeIngredients().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe ingredients first.", Toast.LENGTH_SHORT).show()
            else if(viewModel.getRecipe().value==null && viewModel.getRecipeTags().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe tags first.", Toast.LENGTH_SHORT).show()
            else if(viewModel.getRecipe().value==null && viewModel.getRecipeSteps().value!!.isEmpty())
                Toast.makeText(requireContext(), "Please add recipe steps first.", Toast.LENGTH_SHORT).show()
            else if(difficultyOptions.checkedRadioButtonId==-1)
                Toast.makeText(requireContext(), "Please choose a difficulty first.", Toast.LENGTH_SHORT).show()
            else
            {
                var diff:String?=null
                if(difficultyOptions.checkedRadioButtonId==R.id.easyOption)
                    diff="Easy"
                else if(difficultyOptions.checkedRadioButtonId==R.id.mediumOption)
                    diff="Medium"
                else
                    diff="Hard"

                if(viewModel.getRecipe().value!=null)
                {
                    viewModel.getRecipe().value!!.recipeName=recipeName.text.toString()
                    viewModel.getRecipe().value!!.recipeIngredientsOverview=recipeIngredientDetails.text.toString()
                    viewModel.getRecipe().value!!.recipeOwner=Hawk.get(Constants.LOGGEDIN_USERID)
                    viewModel.getRecipe().value!!.recipeDifficulty=diff
                }
                else
                {
                    if(viewModel.newRecipePicUri==null)
                        viewModel.newRecipePicUri=resourceToUri(requireContext(), R.drawable.no_photo)
                    viewModel.setRecipe(Recipe(UUID.randomUUID().toString(), recipeName.text.toString(), Hawk.get(Constants.LOGGEDIN_USERID), diff, 0,
                        overview = recipeIngredientDetails.text.toString()))
                    for(ingredient in viewModel.getRecipeIngredients().value!!)
                        viewModel.getRecipe().value!!.addIngredient(ingredient)
                    for(step in viewModel.getRecipeSteps().value!!)
                        viewModel.getRecipe().value!!.addStage(step)
                    for(tag in viewModel.getRecipeTags().value!!)
                        viewModel.getRecipe().value!!.addTag(tag)
                }
                viewModel.saveRecipe()
                addedRecipesViewModel.setEditRecipe(false)
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
        recipeStepsRVAdapter = RecipeNewStepsRVAdapter(requireContext(),this)
        recipeStepsRV?.adapter = recipeStepsRVAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val recipePic=view?.findViewById<ImageView>(R.id.recipePic)
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.getData()
            recipePic?.setImageURI(selectedImageUri)
            viewModel.newRecipePicUri=selectedImageUri
        }
    }

    private fun addTags() {
        val dialog = Dialog(requireContext())
        dialog?.setContentView(R.layout.add_tags_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()

        var tagsRVAdapter: TagsRVAdapter?=null
        val addTagsRV = dialog?.findViewById<RecyclerView>(R.id.addTagsRV)
        addTagsRV?.layoutManager = GridLayoutManager(requireActivity(), 2)
        tagsRVAdapter = TagsRVAdapter(requireContext(), this)
        addTagsRV?.adapter = tagsRVAdapter

        if(viewModel.getRecipe().value!=null)
        {
            tagsRVAdapter.setTags(viewModel.allTags)
            tagsRVAdapter.setChosen(viewModel.getRecipe().value!!.getRecipeTags())
            tagsRVAdapter.notifyDataSetChanged()
        }
        else
        {
            tagsRVAdapter.setTags(viewModel.allTags)
            tagsRVAdapter.setChosen(viewModel.getRecipeTags().value!!)
            tagsRVAdapter.notifyDataSetChanged()
        }

        val saveButton = dialog.findViewById<LinearLayout>(R.id.doneButton)
        saveButton?.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun addIngredients()
    {
        val dialog = Dialog(requireContext())
        dialog?.setContentView(R.layout.add_ingredients_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()

        var ingredientsRVAdapter: IngredientsRVAdapter?=null
        val ingredientsList = dialog?.findViewById<RecyclerView>(R.id.ingredientsList)
        ingredientsList?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ingredientsRVAdapter = IngredientsRVAdapter(requireContext(), this)
        ingredientsList?.adapter = ingredientsRVAdapter

        if(viewModel.getRecipe().value!=null)
        {
            ingredientsRVAdapter.setIngredients(viewModel.allIngredients)
            ingredientsRVAdapter.setChosen(viewModel.getRecipe().value!!.getRecipeIngredients())
            ingredientsRVAdapter.notifyDataSetChanged()
        }
        else
        {
            ingredientsRVAdapter.setIngredients(viewModel.allIngredients)
            if(!viewModel.getRecipeIngredients().value!!.isEmpty())
                ingredientsRVAdapter.setChosen(viewModel.getRecipeIngredients().value!!)
            ingredientsRVAdapter.notifyDataSetChanged()
        }

        val searchBar=dialog?.findViewById<androidx.appcompat.widget.SearchView>(R.id.ingredientSearchBar)
        searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchBar.clearFocus()
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<String>()
                    for(ingredient in viewModel.allIngredients)
                        if(ingredient.startsWith(query.lowercase()))
                            tempArray.add(ingredient)
                    ingredientsRVAdapter?.setIngredients(tempArray)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    ingredientsRVAdapter?.setIngredients(viewModel.allIngredients)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false

            }
            override fun onQueryTextChange(query: String): Boolean {
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<String>()
                    for(ingredient in viewModel.allIngredients)
                        if(ingredient.startsWith(query.lowercase()))
                            tempArray.add(ingredient)
                    ingredientsRVAdapter?.setIngredients(tempArray)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    ingredientsRVAdapter?.setIngredients(viewModel.allIngredients)
                    ingredientsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false
            }
        })

        val saveButton = dialog.findViewById<LinearLayout>(R.id.doneButton)
        saveButton?.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onSelectTag(selectedTag: String) {
        viewModel.addTag(selectedTag)
    }

    override fun deselectTag(deSelectedTag: String) {
        viewModel.removeTag(deSelectedTag)
    }

    override fun onSelectIngredient(selectedIngredient: String) {
        viewModel.addIngredient(selectedIngredient)
    }

    override fun deselectIngredient(deSelectedIngredient: String) {
        viewModel.removeIngredient(deSelectedIngredient)
    }

    fun resourceToUri(context: Context, resID:Int):Uri
    {
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID) )
    }

    override fun onStepDeleted(stepText: String) {
        viewModel.removeStep(stepText)
    }
}