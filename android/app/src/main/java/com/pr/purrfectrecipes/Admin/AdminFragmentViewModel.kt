package com.pr.purrfectrecipes.Admin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminFragmentViewModel:ViewModel()
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}

    fun setView(newView: View?)
    {
        view.value=newView
    }
}