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

    private val diffHomeSort=MutableLiveData<SortMethods?>()
        fun getDiffHomeSort():LiveData<SortMethods?> {return diffHomeSort}
    private val popHomeSort=MutableLiveData<SortMethods?>()
        fun getPopHomeSort():LiveData<SortMethods?> {return popHomeSort}

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

}