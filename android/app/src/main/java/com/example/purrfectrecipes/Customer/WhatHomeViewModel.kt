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

    private val showResults=MutableLiveData<Boolean?>()
        fun getShowResults():LiveData<Boolean?>{return showResults}

    private val wantedIngredients= MutableLiveData<ArrayList<String>>()
        fun getWantedIngredients(): LiveData<ArrayList<String>> {return wantedIngredients}
    private val notWantedIngredients= MutableLiveData<ArrayList<String>>()
        fun getNotWantedIngredients(): LiveData<ArrayList<String>> {return notWantedIngredients}

    init{
        editWanted.value=false
        editNotWanted.value=false
        showResults.value=false

        wantedIngredients.value=ArrayList<String>()
        notWantedIngredients.value=ArrayList<String>()
    }

    fun setEditWanted(bool:Boolean)
    {
        editWanted.value=bool
    }

    fun setEditNotWanted(bool:Boolean)
    {
        editNotWanted.value=bool
    }

    fun setShowResult(bool:Boolean)
    {
        showResults.value=bool
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    fun setWantedIngredients(ings:ArrayList<String>)
    {
        wantedIngredients.value?.clear()
        wantedIngredients.value?.addAll(ings)
    }

    fun setNotWantedIngredients(ings:ArrayList<String>)
    {
        notWantedIngredients.value?.clear()
        notWantedIngredients.value?.addAll(ings)
    }

    fun resetIngredients()
    {
        notWantedIngredients.value?.clear()
        wantedIngredients.value?.clear()
    }

}