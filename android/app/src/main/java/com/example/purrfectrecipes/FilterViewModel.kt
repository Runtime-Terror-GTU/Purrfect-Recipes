package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.FilterVMRepConnector

class FilterViewModel: ViewModel(), FilterVMRepConnector
{
    private val allTags= MutableLiveData<ArrayList<String>>()
        fun getTags(): LiveData<ArrayList<String>> {return allTags}

    private val repository=FilterRepository(this)
    val tempTags=ArrayList<String>()
    val tempDifficulties=ArrayList<String>()

    private val chosenDifficultiesHome=MutableLiveData<ArrayList<String>>()
        fun getChosenDifficultiesHome():LiveData<ArrayList<String>>{return chosenDifficultiesHome}
    private val chosenTagsHome=MutableLiveData<ArrayList<String>>()
        fun getChosenTagsHome():LiveData<ArrayList<String>>{return chosenTagsHome}

    init{
        repository.retrieveTags()
        chosenTagsHome.value= ArrayList()
        chosenDifficultiesHome.value= ArrayList()
    }

    override fun onTagsRetrieved(tags: ArrayList<String>) {
        allTags.value=tags
    }

    fun setHomeTags(tags:ArrayList<String>)
    {
        chosenTagsHome.value=ArrayList()
        chosenTagsHome.value?.addAll(tags)
    }

    fun setHomeDifficulties(diffs:ArrayList<String>)
    {
        chosenDifficultiesHome.value=ArrayList()
        chosenDifficultiesHome.value?.addAll(diffs)
    }
}