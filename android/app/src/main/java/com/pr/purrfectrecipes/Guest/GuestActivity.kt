package com.pr.purrfectrecipes.Guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pr.purrfectrecipes.*
import com.pr.purrfectrecipes.Customer.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class GuestActivity : AppCompatActivity() {

    private val recipesViewModel:RecipesHomeViewModel by viewModels()
    private val sortViewModel: SortViewModel by viewModels()
    private val filterViewModel:FilterViewModel by viewModels()
    private val whatViewModel: WhatHomeViewModel by viewModels()
    private val whatResViewModel:WhatresHomeViewModel by viewModels()
    private val editIngredientViewModel:EditIngredientsViewModel by viewModels()
    private val recipeViewModel:RecipeViewModel by viewModels()

    var navHostFragment:NavHostFragment?=null
    var navController: NavController?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        val navigationBarGuest=findViewById<BottomNavigationView>(R.id.navigationBarGuest)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment?.navController
        NavigationUI.setupWithNavController(navigationBarGuest, navController!!)
        navigationBarGuest.setOnItemReselectedListener {
            if(it==navigationBarGuest.menu.getItem(0)) {
                val homeViewModel:HomeFragmentViewModel by viewModels()
                homeViewModel.setView(null)
                navController?.popBackStack(R.id.sortFragment, true)
                navController?.popBackStack(R.id.filterFragment, true)
                navController?.popBackStack(R.id.homeFragment, true)
                navController?.navigate(R.id.homeFragment)
            }
        }
        recipesViewModel.getSort().observe(this,{
            if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_sortFragment)
            }
            else if(recipesViewModel.getSort().value!=null && recipesViewModel.getSort().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
            }
        })
        recipesViewModel.getFilter().observe(this, {
            if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_filterFragment)
            }
            else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        whatResViewModel.getSort().observe(this,{
            if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_sortFragment)
            }
            else if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
            }
        })
        whatResViewModel.getFilter().observe(this, {
            if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_filterFragment)
            }
            else if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                navigationBarGuest.visibility=View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        whatViewModel.getEditWanted().observe(this, {
            if(whatViewModel.getEditWanted().value!=null && whatViewModel.getEditWanted().value==true)
            {
                navController?.popBackStack(R.id.editIngredientsFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_editIngredientsFragment)
            }
            else if(whatViewModel.getEditWanted().value!=null && whatViewModel.getEditWanted().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarGuest.visibility=View.VISIBLE
            }
        })

        whatViewModel.getEditNotWanted().observe(this, {
            if(whatViewModel.getEditNotWanted().value!=null && whatViewModel.getEditNotWanted().value==true)
            {
                navController?.popBackStack(R.id.editIngredientsFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_editIngredientsFragment)
            }
            else if(whatViewModel.getEditNotWanted().value!=null && whatViewModel.getEditNotWanted().value==false){
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarGuest.visibility=View.VISIBLE
            }
        })

        recipesViewModel.getShownRecipe().observe(this,{
            if(recipesViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarGuest.visibility=View.VISIBLE
            }
        })

        whatResViewModel.getShownRecipe().observe(this,{
            if(whatResViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarGuest.visibility=View.GONE
                navController?.navigate(R.id.action_homeFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.homeFragment, false)
                editIngredientViewModel.resetTemp()
                navigationBarGuest.visibility=View.VISIBLE
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
        else if(recipesViewModel.getFilter().value!=null && recipesViewModel.getFilter().value==true)
            recipesViewModel.setFilter(false)
        else if(whatResViewModel.getSort().value!=null && whatResViewModel.getSort().value==true) {
            sortViewModel.resetWhatSort()
            whatResViewModel.setSort(false)
            Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(whatResViewModel.getFilter().value!=null && whatResViewModel.getFilter().value==true) {
            whatResViewModel.setFilter(false)
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
        else
            super.onBackPressed()
    }






}