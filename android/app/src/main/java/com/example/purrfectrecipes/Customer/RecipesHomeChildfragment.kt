package com.example.purrfectrecipes.Customer

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import android.graphics.BitmapFactory
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.purrfectrecipes.*
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener
import com.example.purrfectrecipes.Guest.GuestActivity
import com.orhanobut.hawk.Hawk
import java.io.ByteArrayInputStream


class RecipesHomeChildfragment: Fragment(R.layout.childfragment_home_recipes), RecipeOnClickListener
{
    private val viewModel: RecipesHomeViewModel by activityViewModels()
    private var recipesRVAdapter:HomePageRVAdapter?=null
    private val sortViewModel:SortViewModel by activityViewModels()
    private val filterViewModel:FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
        val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")
        val storageRef=FirebaseStorage.getInstance().getReference().child("Recipe Pictures")

        val id1="7686f73a-7a30-4e90-96d3-5ddda964fbcd"
        val id2="1098788c-f571-4f8a-ab5f-1d84ead8123a"
        val id3="cbb7f974-af8f-4ba8-af10-73813e61fcc0"
        val id4="6e92e147-450c-4fae-bcbe-875c7f86e80e"
        val id5="9f827e72-b637-4628-91ce-0ca9990e1c1e"
        recipesRef.child(id1).child(Constants.R_RECIPENAME).setValue("Cinnamon Buns")
        recipesRef.child(id1).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id1).child(Constants.R_RECIPEDIFFICULTY).setValue("Medium")
        recipesRef.child(id1).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("13")
        var uri=resourceToUri(requireContext(), R.drawable.cinnamon_buns)
        //storageRef.child(id1).putFile(uri)

        recipesRef.child(id2).child(Constants.R_RECIPENAME).setValue("Apple Pie")
        recipesRef.child(id2).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id2).child(Constants.R_RECIPEDIFFICULTY).setValue("Medium")
        recipesRef.child(id2).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("21")
        uri=resourceToUri(requireContext(), R.drawable.apple_pie)
        //storageRef.child(id2).putFile(uri)

        recipesRef.child(id3).child(Constants.R_RECIPENAME).setValue("Cheesecake")
        recipesRef.child(id3).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id3).child(Constants.R_RECIPEDIFFICULTY).setValue("Hard")
        recipesRef.child(id3).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("35")
        uri=resourceToUri(requireContext(), R.drawable.cheesecake)
        //storageRef.child(id3).putFile(uri)

        recipesRef.child(id4).child(Constants.R_RECIPENAME).setValue("Lemonade")
        recipesRef.child(id4).child(Constants.R_RECIPEOWNER).setValue("14e7855c-a9ba-4b99-9cab-1233b2d9c6be")
        recipesRef.child(id4).child(Constants.R_RECIPEDIFFICULTY).setValue("Easy")
        recipesRef.child(id4).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("15")
        uri=resourceToUri(requireContext(), R.drawable.lemonade)
        //storageRef.child(id4).putFile(uri)

        recipesRef.child(id5).child(Constants.R_RECIPENAME).setValue("Brownie")
        recipesRef.child(id5).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id5).child(Constants.R_RECIPEDIFFICULTY).setValue("Hard")
        recipesRef.child(id5).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("106")
        uri=resourceToUri(requireContext(), R.drawable.brownie)
        //storageRef.child(id5).putFile(uri)

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                viewModel.setShownRecipe(null)
                sortViewModel.resetHomeSort()
                filterViewModel.resetHomeFilter()
                viewModel.resetRecipeArray()
                sortViewModel.setHomeSortId(-1)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        setRVAdapter()

        viewModel.getRecipes().observe(viewLifecycleOwner, {
            if(viewModel.getRecipes().value!=null) {
                recipesRVAdapter?.setRecipes(viewModel.getRecipes().value!!)
                recipesRVAdapter?.notifyDataSetChanged()
            }
        })

        viewModel.getRecipeOfTheDay().observe(viewLifecycleOwner,{
            if(viewModel.getRecipeOfTheDay().value!=null)
            {
                val recipeName=view.findViewById<TextView>(R.id.dayRecipeName)
                val recipeOwner=view.findViewById<TextView>(R.id.dayRecipeOwner)
                val recipeDifficulty=view.findViewById<TextView>(R.id.dayRecipeDifficulty)
                val recipeType=view.findViewById<TextView>(R.id.dayRecipeType)
                val recipeLikes=view.findViewById<TextView>(R.id.dayRecipeLikes)
                val recipePic=view.findViewById<ImageView>(R.id.dayRecipePic)
                recipeName.text=viewModel.getRecipeOfTheDay().value?.recipeName
                recipeOwner.text="by "+viewModel.getRecipeOfTheDay().value?.recipeOwner
                recipeDifficulty.text=viewModel.getRecipeOfTheDay().value?.recipeDifficulty
                recipeType.text=viewModel.getRecipeOfTheDay().value?.getRecipeTags()?.get(0)
                recipeLikes.text=viewModel.getRecipeOfTheDay().value?.recipeLikes.toString()
                if(viewModel.getRecipeOfTheDay().value?.recipePictureURL!=" ")
                {
                    Glide.with(requireContext())
                        .load(viewModel.getRecipeOfTheDay().value?.recipePictureURL)
                        .into(recipePic)
                }
                val homePage=view.findViewById<LinearLayout>(R.id.homePage)
                val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
                loadingBar.visibility=View.GONE
                homePage.visibility=View.VISIBLE
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
            Hawk.put(Constants.SORT_DIRECTION, Constants.MAIN_TO_SORT)
        }

        val filterButton=view.findViewById<Button>(R.id.filterButton)
        filterButton.setOnClickListener{
            viewModel.setFilter(true)
            Hawk.put(Constants.FILTER_DIRECTION, Constants.MAIN_TO_FILTER)
        }

        sortViewModel.getDiffHomeSort().observe(viewLifecycleOwner, {
            redoOperations()
        })

        sortViewModel.getPopHomeSort().observe(viewLifecycleOwner,{
            redoOperations()
        })

        filterViewModel.getChosenTagsHome().observe(viewLifecycleOwner, {
            redoOperations()
        })

        filterViewModel.getChosenDifficultiesHome().observe(viewLifecycleOwner, {
            redoOperations()
        })

        val recipeOfTheDay=view.findViewById<CardView>(R.id.recipeOfTheDay)
        recipeOfTheDay.setOnClickListener {
            onRecipeClick(viewModel.getRecipeOfTheDay().value!!.getRecipeID())
        }

        val purrfectButton=view.findViewById<CardView>(R.id.purrfectButton)
        purrfectButton.setOnClickListener {

        }

    }
    fun resourceToUri(context:Context, resID:Int):Uri
    {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
            context.getResources().getResourcePackageName(resID) + '/' +
            context.getResources().getResourceTypeName(resID) + '/' +
            context.getResources().getResourceEntryName(resID) )
    }

    fun setRVAdapter()
    {
        val recipesGridView = view?.findViewById<RecyclerView>(R.id.recipesGridView)
        recipesGridView?.layoutManager = GridLayoutManager(requireActivity(), 2)
        recipesRVAdapter = HomePageRVAdapter(requireContext(), this)
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
        if(filterViewModel.getChosenTagsHome().value!= null && filterViewModel.getChosenTagsHome().value!!.size!=0)
            viewModel.applyTagFilters(filterViewModel.getChosenTagsHome().value!!)
        if(filterViewModel.getChosenDifficultiesHome().value!= null && filterViewModel.getChosenDifficultiesHome().value!!.size!=0)
            viewModel.applyDifficultyFilters(filterViewModel.getChosenDifficultiesHome().value!!)

        //Redo sort
        if(sortViewModel.getDiffHomeSort().value!=null && sortViewModel.getDiffHomeSort().value==SortMethods.difMintoMax)
            viewModel.sortDiffMin()
        else if(sortViewModel.getDiffHomeSort().value!=null && sortViewModel.getDiffHomeSort().value==SortMethods.difMaxtoMin)
            viewModel.sortDiffMax()
        if(sortViewModel.getPopHomeSort().value!=null && sortViewModel.getPopHomeSort().value==SortMethods.popMaxtoMin)
            viewModel.sortPopMax()
        else if(sortViewModel.getPopHomeSort().value!=null && sortViewModel.getPopHomeSort().value==SortMethods.popMintoMax)
            viewModel.sortPopMin()
    }

    override fun onRecipeClick(recipeId: String) {
        viewModel.setShownRecipe(recipeId)
    }

}