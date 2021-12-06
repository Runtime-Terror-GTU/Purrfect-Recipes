package com.example.purrfectrecipes.Moderator

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecipesModeratorViewModel: ViewModel()
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}

    fun setView(newView: View)
    {
        view.value=newView
    }
}