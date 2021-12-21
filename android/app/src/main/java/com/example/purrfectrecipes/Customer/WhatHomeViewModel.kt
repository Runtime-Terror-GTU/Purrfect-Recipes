package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WhatHomeViewModel: ViewModel()
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}
    private val editWanted=MutableLiveData<Boolean?>()
        fun getEditWanted():LiveData<Boolean?>{return editWanted}
    private val editNotWanted=MutableLiveData<Boolean?>()
        fun getEditNotWanted():LiveData<Boolean?>{return editNotWanted}

    init{
        editWanted.value=false
        editNotWanted.value=false
    }

    fun setEditWanted(bool:Boolean)
    {
        editWanted.value=bool
    }

    fun setEditNotWanted(bool:Boolean)
    {
        editNotWanted.value=bool
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

}