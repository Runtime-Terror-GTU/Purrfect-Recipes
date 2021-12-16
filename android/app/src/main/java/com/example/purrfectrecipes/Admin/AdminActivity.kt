package com.example.purrfectrecipes.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Guest.GuestActivity
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val navigationBarAdmin=findViewById<BottomNavigationView>(R.id.navigationBarAdmin)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationBarAdmin, navController)
        navigationBarAdmin.setOnItemReselectedListener {
            if(it==navigationBarAdmin.menu.getItem(0)) {
                val fragmentViewModel:AdminFragmentViewModel by viewModels()
                fragmentViewModel.setView(null)
                navController.popBackStack(R.id.adminFragment, true)
                navController.navigate(R.id.adminFragment)
            }
        }
        navigationBarAdmin.menu.get(1).setOnMenuItemClickListener{
            Hawk.delete(Constants.LOGGEDIN_USERID)
            Hawk.delete(Constants.LOGGEDIN_USER_STATUS)

            val intent= Intent(this, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent)
            finish()
            true
        }
    }
}

