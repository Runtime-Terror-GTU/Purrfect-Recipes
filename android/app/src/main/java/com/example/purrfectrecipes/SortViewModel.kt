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
    private val diffSort=MutableLiveData<SortMethods?>()
        fun getDiffSort():LiveData<SortMethods?> {return diffSort}
    private val popSort=MutableLiveData<SortMethods?>()
        fun getPopSort():LiveData<SortMethods?> {return popSort}

    init{
        checkHomeSortId.value=-1
    }

    fun setHomeSortId(newId: Int)
    {
        if(checkHomeSortId.value!=newId)
            checkHomeSortId.value=newId
    }

}