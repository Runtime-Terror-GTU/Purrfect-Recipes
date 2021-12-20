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

    init{
        repository.retrieveTags()
    }

    override fun onTagsRetrieved(tags: ArrayList<String>) {
        allTags.value=tags
    }
}