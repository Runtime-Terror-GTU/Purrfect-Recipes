package com.example.purrfectrecipes.Admin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddAdminViewModel: ViewModel()
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
}