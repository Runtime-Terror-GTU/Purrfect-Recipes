package com.example.purrfectrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.activity.viewModels
import com.example.purrfectrecipes.Admin.AdminActivity
import com.example.purrfectrecipes.Customer.CustomerActivity
import com.example.purrfectrecipes.Moderator.ModeratorActivity
import com.example.purrfectrecipes.User.Admin
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.CustomerStatus
import com.example.purrfectrecipes.User.Moderator
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.hawk.Hawk
import java.util.*

class SplashActivity : AppCompatActivity()
{
    private val viewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Hawk.init(this).build()
        initiateAutoLogIn()

        viewModel.getRetrievedUser().observe(this, {

            Log.i("here", viewModel.getRetrievedUser().value.toString())
            if(viewModel.getRetrievedUser().value!=null)
                Hawk.put(Constants.LOGGEDIN_USERID, viewModel.getRetrievedUser().value!!.getUserID())

            if(viewModel.getRetrievedUser().value is Customer)
                startCustomerActivity()
            else if(viewModel.getRetrievedUser().value is Admin)
                startAdminActivity()
            else if(viewModel.getRetrievedUser().value is Moderator)
                startModeratorActivity()
            else if(viewModel.getRetrievedUser().value==null)
            {
                val intent= Intent(this, StartActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent)
                finish()
            }
        })

    }

    private fun initiateAutoLogIn()
    {
        val userID=Hawk.get<String>(Constants.LOGGEDIN_USERID)

        if(userID!=null)
            viewModel.logIn(userID)
        else
        {
            val intent= Intent(this, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            startActivity(intent)
            finish()
        }

    }

    private fun startCustomerActivity()
    {
        val intent= Intent(this, CustomerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent)
        finish()
    }

    private fun startAdminActivity()
    {
        val intent= Intent(this, AdminActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent)
        finish()
    }

    private fun startModeratorActivity()
    {

        val intent= Intent(this, ModeratorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent)
        finish()
    }
}