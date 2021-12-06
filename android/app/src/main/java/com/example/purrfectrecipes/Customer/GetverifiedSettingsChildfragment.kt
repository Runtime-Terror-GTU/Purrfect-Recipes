package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R

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
    }

}