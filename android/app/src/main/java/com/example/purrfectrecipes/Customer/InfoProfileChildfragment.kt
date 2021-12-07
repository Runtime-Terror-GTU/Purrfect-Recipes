package com.example.purrfectrecipes.Customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.StartActivity
import com.orhanobut.hawk.Hawk

class InfoProfileChildfragment: Fragment(R.layout.childfragment_profile_info)
{
    private var fragmentView:View?=null
    private val viewModel: AddedrecipesProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        if(viewModel.getView().value!=null)
            fragmentView=viewModel.getView().value
        else
        {
            fragmentView=super.onCreateView(inflater, container, savedInstanceState)
            viewModel.setView(fragmentView!!)
        }

        val logoutButton=fragmentView?.findViewById<ImageView>(R.id.logoutButton)

        logoutButton?.setOnClickListener {
            Hawk.delete(Constants.LOGGEDIN_USERID)
            Hawk.delete(Constants.LOGGEDIN_USER_STATUS)

            val intent= Intent(context, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent)
            activity?.finish()
            true
        }

        return fragmentView
    }



}