package com.pr.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SortViewModel: ViewModel()
{
    private var checkHomeSortId= MutableLiveData<Int>()
        fun getHomeSortId(): LiveData<Int> {return checkHomeSortId}
    private var checkWhatSortId= MutableLiveData<Int>()
        fun getWhatSortId(): LiveData<Int> {return checkWhatSortId}
    private var checkAddedSortId= MutableLiveData<Int>()
        fun getAddedSortId(): LiveData<Int> {return checkAddedSortId}
    private var checkPurrfectedSortId= MutableLiveData<Int>()
        fun getPurrfectedSortId(): LiveData<Int> {return checkPurrfectedSortId}
    private var checkModeratorSortId= MutableLiveData<Int>()
        fun getModeratorSortId(): LiveData<Int> {return checkModeratorSortId}

    private val diffHomeSort=MutableLiveData<SortMethods?>()
        fun getDiffHomeSort():LiveData<SortMethods?> {return diffHomeSort}
    private val popHomeSort=MutableLiveData<SortMethods?>()
        fun getPopHomeSort():LiveData<SortMethods?> {return popHomeSort}

    private val diffWhatSort=MutableLiveData<SortMethods?>()
        fun getDiffWhatSort():LiveData<SortMethods?> {return diffWhatSort}
    private val popWhatSort=MutableLiveData<SortMethods?>()
        fun getPopWhatSort():LiveData<SortMethods?> {return popWhatSort}

    private val diffAddedSort=MutableLiveData<SortMethods?>()
        fun getDiffAddedSort():LiveData<SortMethods?> {return diffAddedSort}
    private val popAddedSort=MutableLiveData<SortMethods?>()
        fun getPopAddedSort():LiveData<SortMethods?> {return popAddedSort}

    private val diffPurrfectedSort=MutableLiveData<SortMethods?>()
        fun getDiffPurrfectedSort():LiveData<SortMethods?> {return diffPurrfectedSort}
    private val popPurrfectedSort=MutableLiveData<SortMethods?>()
        fun getPopPurrfectedSort():LiveData<SortMethods?> {return popPurrfectedSort}

    private val diffModeratorSort=MutableLiveData<SortMethods?>()
        fun getDiffModeratorSort():LiveData<SortMethods?> {return diffModeratorSort}
    private val popModeratorSort=MutableLiveData<SortMethods?>()
        fun getPopModeratorSort():LiveData<SortMethods?> {return popModeratorSort}

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
        checkHomeSortId.value=-1
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
        checkWhatSortId.value=-1
    }

    fun setAddedSortId(newId: Int)
    {
        if(checkAddedSortId.value!=newId)
            checkAddedSortId.value=newId
    }

    fun setDiffAddedSort(method:SortMethods?)
    {
        diffAddedSort.value=method
    }

    fun setPopAddedSort(method:SortMethods?)
    {
        popAddedSort.value=method
    }

    fun resetAddedSort()
    {
        diffAddedSort.value=null
        popAddedSort.value=null
        checkAddedSortId.value=-1
    }

    fun setPurrfectedSortId(newId: Int)
    {
        if(checkPurrfectedSortId.value!=newId)
            checkPurrfectedSortId.value=newId
    }

    fun setDiffPurrfectedSort(method:SortMethods?)
    {
        diffPurrfectedSort.value=method
    }

    fun setPopPurrfectedSort(method:SortMethods?)
    {
        popPurrfectedSort.value=method
    }

    fun resetPurrfectedSort()
    {
        diffPurrfectedSort.value=null
        popPurrfectedSort.value=null
        checkPurrfectedSortId.value=-1
    }

    fun setModeratorSortId(newId: Int)
    {
        if(checkModeratorSortId.value!=newId)
            checkModeratorSortId.value=newId
    }

    fun setDiffModeratorSort(method:SortMethods?)
    {
        diffModeratorSort.value=method
    }

    fun setPopModeratorSort(method:SortMethods?)
    {
        popModeratorSort.value=method
    }

    fun resetModeratorSort()
    {
        diffModeratorSort.value=null
        popModeratorSort.value=null
        checkModeratorSortId.value=-1
    }

}