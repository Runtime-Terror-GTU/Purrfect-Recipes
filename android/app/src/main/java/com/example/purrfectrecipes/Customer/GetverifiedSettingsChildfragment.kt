package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk

class GetverifiedSettingsChildfragment: Fragment(R.layout.childfragment_settings_getverified)
{

    private val viewModel: SettingsSharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getVerifyView().value!=null)
            super.onViewCreated(viewModel.getVerifyView().value!!, savedInstanceState)
        else
        {
            viewModel.setVerifyView(view)
            super.onViewCreated(view, savedInstanceState)
        }

        val enterVerifyButton=view.findViewById<TextView>(R.id.getVerifyButton)
        val emailInput = view.findViewById<EditText>(R.id.userEmailInput)

        viewModel.getInputUserEmail().observe(viewLifecycleOwner, {
            if(viewModel.getInputUserEmail().value!=null)
                emailInput.setText(viewModel.getInputUserEmail().value.toString())
        })


    }

}