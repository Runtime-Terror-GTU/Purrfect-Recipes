package com.pr.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel: ViewModel()
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}

    fun setView(newView: View?)
    {
        view.value=newView
    }

}