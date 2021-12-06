package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R

class SuggestSettingsChildfragment: Fragment(R.layout.childfragment_settings_suggest)
{
    private val viewModel: SettingsSharedViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getSuggestView().value!=null)
            super.onViewCreated(viewModel.getSuggestView().value!!, savedInstanceState)
        else
        {
            viewModel.setSuggestView(view)
            super.onViewCreated(view, savedInstanceState)
        }
    }

}