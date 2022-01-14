package com.pr.purrfectrecipes.Moderator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class ModeratorActivity : AppCompatActivity() {

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
    }
}