package com.example.purrfectrecipes

import android.content.Intent
import android.graphics.Typeface
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.example.purrfectrecipes.Guest.GuestActivity
import com.google.android.material.textfield.TextInputLayout

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val usernameLayout=findViewById<TextInputLayout>(R.id.username_layout)
        val passwordLayout=findViewById<TextInputLayout>(R.id.password_layout)
        var ty= ResourcesCompat.getFont(this, R.font.josefin_slab)
        ty=Typeface.create(ty, Typeface.ITALIC)
        usernameLayout.typeface=ty
        passwordLayout.typeface=ty

    }

    fun onRegisterSwitch(view: View)
    {
        val loginScreen=findViewById<LinearLayout>(R.id.login_screen)
        val registerScreen=findViewById<LinearLayout>(R.id.register_screen)
        loginScreen.visibility= View.GONE
        registerScreen.visibility=View.VISIBLE
    }

    fun onLoginSwitch(view: View)
    {
        val loginScreen=findViewById<LinearLayout>(R.id.login_screen)
        val registerScreen=findViewById<LinearLayout>(R.id.register_screen)
        registerScreen.visibility=View.GONE
        loginScreen.visibility=View.VISIBLE
    }

    fun onContinueGuest(view: View)
    {
        val intent= Intent(this, GuestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent)
        finish()
    }
}