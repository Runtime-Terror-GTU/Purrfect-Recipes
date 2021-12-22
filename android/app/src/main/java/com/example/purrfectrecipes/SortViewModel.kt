package com.example.purrfectrecipes

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.User.User

class SortViewModel: ViewModel()
{
    private var checkHomeSortId= MutableLiveData<Int>()
        fun getHomeSortId(): LiveData<Int> {return checkHomeSortId}
    private var checkWhatSortId= MutableLiveData<Int>()
        fun getWhatSortId(): LiveData<Int> {return checkWhatSortId}

    private val diffHomeSort=MutableLiveData<SortMethods?>()
        fun getDiffHomeSort():LiveData<SortMethods?> {return diffHomeSort}
    private val popHomeSort=MutableLiveData<SortMethods?>()
        fun getPopHomeSort():LiveData<SortMethods?> {return popHomeSort}

    private val diffWhatSort=MutableLiveData<SortMethods?>()
        fun getDiffWhatSort():LiveData<SortMethods?> {return diffWhatSort}
    private val popWhatSort=MutableLiveData<SortMethods?>()
        fun getPopWhatSort():LiveData<SortMethods?> {return popWhatSort}

    init{
        checkHomeSortId.value=-1
    }

    fun setHomeSortId(newId: Int)
    {
        if(checkHomeSortId.value!=newId)
            checkHomeSortId.value=newId
    }

    fun setDiffHomeSort(method:SortMethods?)
    {
        diffHomeSort.value=method
    }

    fun setPopHomeSort(method:SortMethods?)
    {
        popHomeSort.value=method
    }

    fun resetHomeSort()
    {
        diffHomeSort.value=null
        popHomeSort.value=null
    }

    fun setWhatSortId(newId: Int)
    {
        if(checkWhatSortId.value!=newId)
            checkWhatSortId.value=newId
    }

    fun setDiffWhatSort(method:SortMethods?)
    {
        diffWhatSort.value=method
    }

    fun setPopWhatSort(method:SortMethods?)
    {
        popWhatSort.value=method
    }

    fun resetWhatSort()
    {
        diffWhatSort.value=null
        popWhatSort.value=null
    }

}