package com.example.purrfectrecipes

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.User.User

class SortViewModel: ViewModel()
{
    private var checkId= MutableLiveData<Int>()
        fun getId(): LiveData<Int> {return checkId}

    init{
        checkId.value=-1
    }

    fun setId(newId: Int)
    {
        if(checkId.value!=newId)
         checkId.value=newId
    }

}