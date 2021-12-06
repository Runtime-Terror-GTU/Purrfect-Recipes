package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R

class ShopSettingsChildFragment: Fragment(R.layout.childfragment_settings_shop)
{
    private val viewModel: SettingsSharedViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if(viewModel.getShopView().value!=null)
            super.onViewCreated(viewModel.getShopView().value!!, savedInstanceState)
        else
        {
            viewModel.setShopView(view)
            super.onViewCreated(view, savedInstanceState)
        }
    }

}