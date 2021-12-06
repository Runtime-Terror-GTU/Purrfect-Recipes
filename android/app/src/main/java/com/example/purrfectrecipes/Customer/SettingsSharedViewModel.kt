package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsSharedViewModel: ViewModel()
{
    private var verifyView= MutableLiveData<View?>()
        fun getVerifyView(): LiveData<View?> {return verifyView}
    private var shopView= MutableLiveData<View?>()
        fun getShopView(): LiveData<View?> {return shopView}
    private var suggestView= MutableLiveData<View?>()
        fun getSuggestView(): LiveData<View?> {return suggestView}

    fun setVerifyView(newView: View)
    {
        verifyView.value=newView
    }

    fun setShopView(newView: View)
    {
        shopView.value=newView
    }

    fun setSuggestView(newView: View)
    {
        suggestView.value=newView
    }
}