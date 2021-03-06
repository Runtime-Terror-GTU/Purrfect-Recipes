package com.pr.purrfectrecipes.Customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pr.purrfectrecipes.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class CustomerActivity : AppCompatActivity() {

    private val homeViewModel:HomeFragmentViewModel by viewModels()
    private val profileViewModel:ProfileFragmentViewModel by viewModels()
    private val settingsViewModel: SettingsFragmentViewModel by viewModels()

    private val recipesViewModel:RecipesHomeViewModel by viewModels()
    private val whatViewModel:WhatHomeViewModel by viewModels()
    private val whatResViewModel:WhatresHomeViewModel by viewModels()
    private val sortViewModel:SortViewModel by viewModels()
    private val filterViewModel:FilterViewModel by viewModels()
    private val editIngredientViewModel:EditIngredientsViewModel by viewModels()
    private val recipeViewModel:RecipeViewModel by viewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by viewModels()
    private val purrfectedRecipesViewModel:PurrfectedrecipesProfileViewModel by viewModels()
    private val infoProfileViewModel: InfoProfileViewModel by viewModels()
    private val editProileViewModel:EditViewModel by viewModels()

    var navHostFragment:NavHostFragment?=null
    var navController: NavController?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        val navigationBarCustomer=findViewById<BottomNavigationView>(R.id.navigationBarCustomer)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        NavigationUI.setupWithNavController(navigationBarCustomer, navController!!)
        navigationBarCustomer.setOnItemReselectedListener {
            if(it==navigationBarCustomer.menu.getItem(0)) {
                homeViewModel.setView(null)
                navController?.popBackStack(R.id.sortFragment, true)
                navController?.popBackStack(R.id.filterFragment, true)
                navController?.popBackStack(R.id.homeFragment, true)
                navController?.navigate(R.id.homeFragment)
            }else if(it==navigationBarCustomer.menu.getItem(1)) {
                profileViewModel.setView(null)
                navController?.popBackStack(R.id.profileFragment, true)
                navController?.navigate(R.id.profileFragment)
            }
            else{
                settingsViewModel.setView(null)
                navController?.popBackStack(R.id.settingsFragment, true)
                navController?.navigate(R.id.settingsFragment)
            }
        }

        recipesViewModel.getSort().observe(this,{
            if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_sortFragment)
            }
            else if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
        recipesViewModel.getFilter().observe(this, {
            if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_filterFragment)
            }
            else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        whatResViewModel.getSort().observe(this,{
            if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_sortFragment)
            }
            else if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
        whatResViewModel.getFilter().observe(this, {
            if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_filterFragment)
            }
            else if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        addedRecipesViewModel.getSort().observe(this,{
            if(addedRecipesViewModel.getSort().value!=null && addedRecipesViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_sortFragment)
            }
            else if(addedRecipesViewModel.getSort().value!=null && addedRecipesViewModel.getSort().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
        addedRecipesViewModel.getFilter().observe(this, {
            if(addedRecipesViewModel.getFilter().value!=null && addedRecipesViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_filterFragment)
            }
            else if(addedRecipesViewModel.getFilter().value!=null && addedRecipesViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        purrfectedRecipesViewModel.getSort().observe(this,{
            if(purrfectedRecipesViewModel.getSort().value!=null && purrfectedRecipesViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_sortFragment)
            }
            else if(purrfectedRecipesViewModel.getSort().value!=null && purrfectedRecipesViewModel.getSort().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })
        purrfectedRecipesViewModel.getFilter().observe(this, {
            if(purrfectedRecipesViewModel.getFilter().value!=null && purrfectedRecipesViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_filterFragment)
            }
            else if(purrfectedRecipesViewModel.getFilter().value!=null && purrfectedRecipesViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        whatViewModel.getEditWanted().observe(this, {
            if(whatViewModel.getEditWanted().value!=null && whatViewModel.getEditWanted().value==true)
            {
                navController?.popBackStack(R.id.editIngredientsFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_editIngredientsFragment)
            }
            else if(whatViewModel.getEditWanted().value!=null && whatViewModel.getEditWanted().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        whatViewModel.getEditNotWanted().observe(this, {
            if(whatViewModel.getEditNotWanted().value!=null && whatViewModel.getEditNotWanted().value==true)
            {
                navController?.popBackStack(R.id.editIngredientsFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_editIngredientsFragment)
            }
            else if(whatViewModel.getEditNotWanted().value!=null && whatViewModel.getEditNotWanted().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        recipesViewModel.getShownRecipe().observe(this,{
            if(recipesViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        whatResViewModel.getShownRecipe().observe(this,{
            if(whatResViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        addedRecipesViewModel.getShownRecipe().observe(this,{
            if(addedRecipesViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        addedRecipesViewModel.getGetVerified().observe(this, {
            if(addedRecipesViewModel.getGetVerified().value!=null && addedRecipesViewModel.getGetVerified().value==true)
            {
                navigationBarCustomer.selectedItemId=R.id.settingsFragment
                addedRecipesViewModel.setGetVerified(false)
            }
        })
        addedRecipesViewModel.getEditRecipe().observe(this,{
            if(addedRecipesViewModel.getEditRecipe().value!=null && addedRecipesViewModel.getEditRecipe().value==true)
            {
                navController?.popBackStack(R.id.editRecipeFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_editRecipeFragment)
            }
            else if(addedRecipesViewModel.getEditRecipe().value!=null && addedRecipesViewModel.getEditRecipe().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
                addedRecipesViewModel.setEditedRecipe(null)
            }
        })

        infoProfileViewModel.getEditProfile().observe(this,{
            if(infoProfileViewModel.getEditProfile().value!=null && infoProfileViewModel.getEditProfile().value==true)
            {
                navController?.popBackStack(R.id.editProfileFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
            else if(infoProfileViewModel.getEditProfile().value!=null && infoProfileViewModel.getEditProfile().value==false){
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })

        purrfectedRecipesViewModel.getShownRecipe().observe(this,{
            if(purrfectedRecipesViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarCustomer.visibility=View.GONE
                navController?.navigate(R.id.action_profileFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.profileFragment, false)
                navigationBarCustomer.visibility=View.VISIBLE
            }
        })


    }
    override fun onBackPressed() {
        val current= navHostFragment?.childFragmentManager?.fragments?.get(0)?.childFragmentManager?.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager?.fragments?.get(0)

        if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true) {
                sortViewModel.resetHomeSort()
                recipesViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true) {
                recipesViewModel.setFilter(false)
            Hawk.delete(Constants.FILTER_DIRECTION)
        }
        else if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==true) {
            sortViewModel.resetWhatSort()
            whatResViewModel.setSort(false)
            Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==true) {
            whatResViewModel.setFilter(false)
            Hawk.delete(Constants.FILTER_DIRECTION)
        }
        else if(addedRecipesViewModel.getSort().value!=null && addedRecipesViewModel.getSort().value==true) {
            sortViewModel.resetAddedSort()
            addedRecipesViewModel.setSort(false)
            Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(addedRecipesViewModel.getFilter().value!=null && addedRecipesViewModel.getFilter().value==true) {
            addedRecipesViewModel.setFilter(false)
            Hawk.delete(Constants.FILTER_DIRECTION)
        }
        else if(purrfectedRecipesViewModel.getSort().value!=null && purrfectedRecipesViewModel.getSort().value==true) {
            sortViewModel.resetPurrfectedSort()
            purrfectedRecipesViewModel.setSort(false)
            Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(purrfectedRecipesViewModel.getFilter().value!=null && purrfectedRecipesViewModel.getFilter().value==true) {
            purrfectedRecipesViewModel.setFilter(false)
            Hawk.delete(Constants.FILTER_DIRECTION)
        }
        else if(whatViewModel.getEditWanted().value!=null && whatViewModel.getEditWanted().value==true) {
            whatViewModel.setEditWanted(false)
        }
        else if(whatViewModel.getEditNotWanted().value!=null && whatViewModel.getEditNotWanted().value==true) {
            whatViewModel.setEditNotWanted(false)
        }
        else if(whatViewModel.getShowResults().value!=null && whatViewModel.getShowResults().value==true && current is WhatresHomeChildfragment) {
            whatViewModel.setShowResult(false)
        }
        else if(recipesViewModel.getShownRecipe().value!=null) {
            recipesViewModel.setShownRecipe(null)
            recipeViewModel.resetRecipe()
        }
        else if(whatResViewModel.getShownRecipe().value!=null) {
            whatResViewModel.setShownRecipe(null)
            recipeViewModel.resetRecipe()
        }
        else if(addedRecipesViewModel.getShownRecipe().value!=null) {
            addedRecipesViewModel.setShownRecipe(null)
            recipeViewModel.resetRecipe()
        }
        else if(addedRecipesViewModel.getEditRecipe().value!=null && addedRecipesViewModel.getEditRecipe().value==true) {
            addedRecipesViewModel.setEditRecipe(false)
        }
        else if(purrfectedRecipesViewModel.getShownRecipe().value!=null) {
            purrfectedRecipesViewModel.setShownRecipe(null)
            recipeViewModel.resetRecipe()
        }
        else if(infoProfileViewModel.getEditProfile().value!=null && infoProfileViewModel.getEditProfile().value==true)
        {
            infoProfileViewModel.setEditProfile(false)
        }
        else
            super.onBackPressed()
    }
}