package com.example.purrfectrecipes

import android.content.Intent
import android.graphics.Typeface
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.example.purrfectrecipes.Admin.AdminActivity
import com.example.purrfectrecipes.Customer.CustomerActivity
import com.example.purrfectrecipes.Guest.GuestActivity
import com.example.purrfectrecipes.Moderator.ModeratorActivity
import com.example.purrfectrecipes.User.Admin
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.Moderator
import com.example.purrfectrecipes.User.User
import com.google.android.material.textfield.TextInputLayout
import com.orhanobut.hawk.Hawk
import java.util.*

class StartActivity : AppCompatActivity() {

    private val viewModel:StartViewModel by viewModels()
    private var loginScreen:LinearLayout?=null
    private var registerScreen:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loginScreen=findViewById(R.id.login_screen)
        registerScreen=findViewById(R.id.register_screen)

        val usernameLayout=findViewById<TextInputLayout>(R.id.username_layout)
        val passwordLayout=findViewById<TextInputLayout>(R.id.password_layout)
        val usernameLayoutR=findViewById<TextInputLayout>(R.id.username_layoutR)
        val emailLayoutR=findViewById<TextInputLayout>(R.id.email_layoutR)
        val passwordLayoutR=findViewById<TextInputLayout>(R.id.password_layoutR)
        val passwordLayoutR2=findViewById<TextInputLayout>(R.id.password_layoutR2)
        var ty= ResourcesCompat.getFont(this, R.font.josefin_slab)
        ty=Typeface.create(ty, Typeface.ITALIC)
        usernameLayout.typeface=ty
        passwordLayout.typeface=ty
        usernameLayoutR.typeface=ty
        emailLayoutR.typeface=ty
        passwordLayoutR.typeface=ty
        passwordLayoutR2.typeface=ty

        viewModel.getAuthenticationState().observe(this, {

            if(viewModel.getRetrievedUser()!=null)
            {
                Hawk.put(Constants.LOGGEDIN_PASS, viewModel.getRetrievedUser()!!.getUserPassword())
                Hawk.put(Constants.LOGGEDIN_USERNAME, viewModel.getRetrievedUser()!!.getUsername())
                Hawk.put(Constants.LOGGEDIN_USERID, viewModel.getRetrievedUser()!!.getUserID())
            }

            if(viewModel.getAuthenticationState().value==AuthenticationStates.FAILED_LOGIN)
                resetLogIn()
            else if(viewModel.getAuthenticationState().value==AuthenticationStates.CUSTOMER)
                startCustomerActivity()
            else if(viewModel.getAuthenticationState().value==AuthenticationStates.ADMIN)
                startAdminActivity()
            else if(viewModel.getAuthenticationState().value==AuthenticationStates.MODERATOR)
                startModeratorActivity()
            else
                resetRegister()
        })

    }

    fun onRegisterSwitch(view: View)
    {
        loginScreen?.visibility= View.GONE
        registerScreen?.visibility=View.VISIBLE
    }

    fun onLoginSwitch(view: View)
    {
        registerScreen?.visibility=View.GONE
        loginScreen?.visibility=View.VISIBLE
    }

    fun onContinueGuest(view: View)
    {
        val intent= Intent(this, GuestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent)
        finish()
    }

    fun onEnter(view: View)
    {
        val enteredUsernameR=findViewById<AppCompatEditText>(R.id.enteredUsernameR)
        val enteredEmailR=findViewById<AppCompatEditText>(R.id.enteredEmailR)
        val enteredPasswordR=findViewById<AppCompatEditText>(R.id.enteredPasswordR)
        val enteredPasswordR2=findViewById<AppCompatEditText>(R.id.enteredPasswordR2)

        if(loginScreen?.visibility==View.VISIBLE)
            initiateLogin()
        else if(enteredUsernameR.text.toString() == "" || enteredEmailR.text.toString() == ""  || enteredPasswordR.text.toString() == ""  || enteredPasswordR2.text.toString() == "" )
            Toast.makeText(this, "Please enter all of the information.", Toast.LENGTH_SHORT).show()
        else
            initiateRegister()
    }

    fun initiateLogin()
    {
        val enteredUsername=findViewById<AppCompatEditText>(R.id.enteredUsername)
        val enteredPassword=findViewById<AppCompatEditText>(R.id.enteredPassword)
        viewModel.logIn(enteredUsername.text.toString(), enteredPassword.text.toString())
    }

    fun initiateRegister()
    {
        val enteredUsernameR=findViewById<AppCompatEditText>(R.id.enteredUsernameR)
        val enteredEmailR=findViewById<AppCompatEditText>(R.id.enteredEmailR)
        val enteredPasswordR=findViewById<AppCompatEditText>(R.id.enteredPasswordR)
        val enteredPasswordR2=findViewById<AppCompatEditText>(R.id.enteredPasswordR2)

        val newUser: User =Customer(UUID.randomUUID().toString(), enteredUsernameR.text.toString(), enteredEmailR.text.toString())
        while(!newUser.changePassword(enteredPasswordR.text.toString(), enteredPasswordR2.text.toString()))
        {
            Toast.makeText(this, "Passwords do not match. Please enter again.", Toast.LENGTH_SHORT).show()
            enteredPasswordR.setText("")
            enteredPasswordR2.setText("")
            enteredPasswordR2.clearFocus()
            enteredPasswordR.requestFocus()
        }

        viewModel.register(newUser)
    }

    private fun resetLogIn()
    {
        Toast.makeText(this, "You have entered invalid info to log in. Please enter again.", Toast.LENGTH_SHORT).show()

        val enteredUsername=findViewById<AppCompatEditText>(R.id.enteredUsername)
        val enteredPassword=findViewById<AppCompatEditText>(R.id.enteredPassword)
        enteredUsername.setText("")
        enteredPassword.setText("")
        enteredUsername.clearFocus()
        enteredPassword.clearFocus()
    }

    fun resetRegister()
    {
        Toast.makeText(this, "There is a user registrated with this username. Please enter a different username.", Toast.LENGTH_SHORT).show()
        val enteredUsernameR=findViewById<AppCompatEditText>(R.id.enteredUsernameR)
        enteredUsernameR.setText("")
        enteredUsernameR.requestFocus()
    }

    private fun startCustomerActivity()
    {
        val intent= Intent(this, CustomerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent)
        finish()
    }

    private fun startAdminActivity()
    {
        val intent= Intent(this, AdminActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent)
        finish()
    }

    private fun startModeratorActivity()
    {
        val intent= Intent(this, ModeratorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent)
        finish()
    }
}