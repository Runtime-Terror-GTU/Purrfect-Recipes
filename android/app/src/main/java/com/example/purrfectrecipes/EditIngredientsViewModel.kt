package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.EditIngredientsVMRepConnector

class EditIngredientsViewModel: ViewModel(), EditIngredientsVMRepConnector
{
    private val allIngredients= MutableLiveData<ArrayList<String>>()
        fun getIngredients(): LiveData<ArrayList<String>> {return allIngredients}

    private val repository= EditIngredientsRepository(this)

    init{
        repository.retrieveIngredients()
    }

    override fun onIngredientsRetrieved(ingredients: ArrayList<String>) {
        allIngredients.value=ingredients
    }

}