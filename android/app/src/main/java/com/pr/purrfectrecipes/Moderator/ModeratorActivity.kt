package com.pr.purrfectrecipes.Moderator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.*

class ModeratorActivity : AppCompatActivity() {

    private val sortViewModel: SortViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private val recipesModeratorViewModel:RecipesModeratorViewModel by viewModels()
    private val recipeViewModel:RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderator)

        val navigationBarModerator=findViewById<BottomNavigationView>(R.id.navigationBarModerator)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationBarModerator, navController)
        navigationBarModerator.setOnItemReselectedListener {
            if(it==navigationBarModerator.menu.getItem(0)) {
                val moderatorViewModel:ModeratorFragmentViewModel by viewModels()
                moderatorViewModel.setView(null)
                navController.popBackStack(R.id.moderatorFragment, true)
                navController.navigate(R.id.moderatorFragment)
            }
        }
        navigationBarModerator.menu.get(1).setOnMenuItemClickListener{
            Hawk.delete(Constants.LOGGEDIN_USERID)
            Hawk.delete(Constants.LOGGEDIN_USER_STATUS)

            val intent= Intent(this, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)
            finish()
            true
        }

        recipesModeratorViewModel.getSort().observe(this,{
            if(recipesModeratorViewModel.getSort().value!=null && recipesModeratorViewModel.getSort().value==true)
            {
                navController?.popBackStack(R.id.sortFragment, true)
                navigationBarModerator.visibility= View.GONE
                navController?.navigate(R.id.action_moderatorFragment_to_sortFragment)
            }
            else if(recipesModeratorViewModel.getSort().value!=null && recipesModeratorViewModel.getSort().value==false){
                navController?.popBackStack(R.id.moderatorFragment, false)
                navigationBarModerator.visibility= View.VISIBLE
            }
        })
        recipesModeratorViewModel.getFilter().observe(this, {
            if(recipesModeratorViewModel.getFilter().value!=null && recipesModeratorViewModel.getFilter().value==true)
            {
                navController?.popBackStack(R.id.filterFragment, true)
                navigationBarModerator.visibility= View.GONE
                navController?.navigate(R.id.action_moderatorFragment_to_filterFragment)
            }
            else if(recipesModeratorViewModel.getFilter().value!=null && recipesModeratorViewModel.getFilter().value==false){
                navController?.popBackStack(R.id.moderatorFragment, false)
                navigationBarModerator.visibility= View.VISIBLE
                filterViewModel.tempTags.clear()
                filterViewModel.tempDifficulties.clear()
            }
        })

        recipesModeratorViewModel.getShownRecipe().observe(this,{
            if(recipesModeratorViewModel.getShownRecipe().value!=null)
            {
                navController?.popBackStack(R.id.recipeFragment, true)
                navigationBarModerator.visibility=View.GONE
                navController?.navigate(R.id.action_moderatorFragment_to_recipeFragment)
            }
            else
            {
                navController?.popBackStack(R.id.recipesModeratorChildfragment, false)
                navigationBarModerator.visibility=View.VISIBLE
            }
        })
    }

    override fun onBackPressed() {
        if(recipesModeratorViewModel.getSort().value!=null && recipesModeratorViewModel.getSort().value==true) {
            sortViewModel.resetModeratorSort()
            recipesModeratorViewModel.setSort(false)
            Hawk.delete(Constants.SORT_DIRECTION)
        }
        else if(recipesModeratorViewModel.getFilter().value!=null && recipesModeratorViewModel.getFilter().value==true) {
            recipesModeratorViewModel.setFilter(false)
            Hawk.delete(Constants.FILTER_DIRECTION)
        }
        else if(recipesModeratorViewModel.getShownRecipe().value!=null) {
            recipesModeratorViewModel.setShownRecipe(null)
            recipeViewModel.resetRecipe()
        }

        super.onBackPressed()
    }
}