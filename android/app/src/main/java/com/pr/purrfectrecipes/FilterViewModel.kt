package com.pr.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.FilterVMRepConnector

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

    private val chosenDifficultiesWhat=MutableLiveData<ArrayList<String>>()
        fun getChosenDifficultiesWhat():LiveData<ArrayList<String>>{return chosenDifficultiesWhat}
    private val chosenTagsWhat=MutableLiveData<ArrayList<String>>()
        fun getChosenTagsWhat():LiveData<ArrayList<String>>{return chosenTagsWhat}

    private val chosenDifficultiesAdded=MutableLiveData<ArrayList<String>>()
        fun getChosenDifficultiesAdded():LiveData<ArrayList<String>>{return chosenDifficultiesAdded}
    private val chosenTagsAdded=MutableLiveData<ArrayList<String>>()
        fun getChosenTagsAdded():LiveData<ArrayList<String>>{return chosenTagsAdded}

    private val chosenDifficultiesPurrfected=MutableLiveData<ArrayList<String>>()
        fun getChosenDifficultiesPurrfected():LiveData<ArrayList<String>>{return chosenDifficultiesPurrfected}
    private val chosenTagsPurrfected=MutableLiveData<ArrayList<String>>()
        fun getChosenTagsPurrfected():LiveData<ArrayList<String>>{return chosenTagsPurrfected}

    init{
        repository.retrieveTags()
        chosenTagsHome.value= ArrayList()
        chosenDifficultiesHome.value= ArrayList()
        chosenTagsWhat.value= ArrayList()
        chosenDifficultiesWhat.value= ArrayList()
        chosenTagsAdded.value= ArrayList()
        chosenDifficultiesAdded.value= ArrayList()
        chosenTagsPurrfected.value= ArrayList()
        chosenDifficultiesPurrfected.value= ArrayList()

    }

    override fun onTagsRetrieved(tags: ArrayList<String>) {
        allTags.value=tags
    }

    fun setHomeTags(tags:ArrayList<String>)
    {
        chosenTagsHome.value?.clear()
        chosenTagsHome.value?.addAll(tags)
    }

    fun setHomeDifficulties(diffs:ArrayList<String>)
    {
        chosenDifficultiesHome.value?.clear()
        chosenDifficultiesHome.value?.addAll(diffs)
    }

    fun resetHomeFilter()
    {
        chosenDifficultiesHome.value?.clear()
        chosenTagsHome.value?.clear()
    }

    fun setWhatTags(tags:ArrayList<String>)
    {
        chosenTagsWhat.value?.clear()
        chosenTagsWhat.value?.addAll(tags)
    }

    fun setWhatDifficulties(diffs:ArrayList<String>)
    {
        chosenDifficultiesWhat.value?.clear()
        chosenDifficultiesWhat.value?.addAll(diffs)
    }

    fun resetWhatFilter()
    {
        chosenDifficultiesWhat.value?.clear()
        chosenTagsWhat.value?.clear()
    }

    fun setAddedTags(tags:ArrayList<String>)
    {
        chosenTagsAdded.value?.clear()
        chosenTagsAdded.value?.addAll(tags)
    }

    fun setAddedDifficulties(diffs:ArrayList<String>)
    {
        chosenDifficultiesAdded.value?.clear()
        chosenDifficultiesAdded.value?.addAll(diffs)
    }

    fun resetAddedFilter()
    {
        chosenDifficultiesAdded.value?.clear()
        chosenTagsAdded.value?.clear()
    }

    fun setPurrfectedTags(tags:ArrayList<String>)
    {
        chosenTagsPurrfected.value?.clear()
        chosenTagsPurrfected.value?.addAll(tags)
    }

    fun setPurrfectedDifficulties(diffs:ArrayList<String>)
    {
        chosenDifficultiesPurrfected.value?.clear()
        chosenDifficultiesPurrfected.value?.addAll(diffs)
    }

    fun resetPurrfectedFilter()
    {
        chosenDifficultiesPurrfected.value?.clear()
        chosenTagsPurrfected.value?.clear()
    }
}