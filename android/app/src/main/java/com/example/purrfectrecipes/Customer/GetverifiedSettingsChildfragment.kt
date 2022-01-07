package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk

class GetverifiedSettingsChildfragment: Fragment(R.layout.childfragment_settings_getverified)
{

    private val viewModel: SettingsSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getVerifyView().observe(viewLifecycleOwner, {
            if(viewModel.getVerifyView().value!=null)
                super.onViewCreated(viewModel.getVerifyView().value!!, savedInstanceState)
            else
            {
                viewModel.setVerifyView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        val getVerified = view.findViewById<LinearLayout>(R.id.getVerified)
        val gettingVerified = view.findViewById<LinearLayout>(R.id.gettingVerified)
        val alreadyVerified = view.findViewById<LinearLayout>(R.id.alreadyVerified)
        val num = (1000..9999).shuffled().last()

        val enterVerifyButton=view.findViewById<TextView>(R.id.getVerifyButton)
        val emailInput = view.findViewById<EditText>(R.id.userEmailInput)
        var userStatus= "UNVERIFIED"
        viewModel.getInputUserEmail().observe(viewLifecycleOwner, {
            if(viewModel.getInputUserEmail().value!=null)
                emailInput.setText(viewModel.getInputUserEmail().value.toString())
        })
        viewModel.getStatus().observe(viewLifecycleOwner, {
            if(viewModel.getStatus().value!=null){
                userStatus = userStatus.replace("UNVERIFIED",viewModel.getStatus().value.toString(),false)
                pageView()
            }
        })


    }
    fun pageView(){

    }

}