package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.EditIngredientsVMRepConnector

class EditIngredientsViewModel: ViewModel(), EditIngredientsVMRepConnector
{
    private val allIngredients= MutableLiveData<ArrayList<String>>()
        fun getIngredients(): LiveData<ArrayList<String>> {return allIngredients}

    private val wantedIngredients= MutableLiveData<ArrayList<String>>()
        fun getWantedIngredients(): LiveData<ArrayList<String>> {return wantedIngredients}
    private val notWantedIngredients= MutableLiveData<ArrayList<String>>()
        fun getNotWantedIngredients(): LiveData<ArrayList<String>> {return notWantedIngredients}

    val tempIngredients=ArrayList<String>()

    private val repository= EditIngredientsRepository(this)

    init{
        repository.retrieveIngredients()
        wantedIngredients.value=ArrayList<String>()
        notWantedIngredients.value=ArrayList<String>()
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

    fun resetTemp()
    {
        tempIngredients.clear()
    }

    override fun onIngredientsRetrieved(ingredients: ArrayList<String>) {
        allIngredients.value=ingredients
    }

    fun resetIngredients()
    {
        notWantedIngredients.value?.clear()
        wantedIngredients.value?.clear()
    }

}