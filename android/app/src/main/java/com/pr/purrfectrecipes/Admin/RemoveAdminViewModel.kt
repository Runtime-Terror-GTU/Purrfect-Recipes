package com.pr.purrfectrecipes.Admin

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.AdminRemoveModoratorVMRepConnector
import com.pr.purrfectrecipes.User.Customer

class RemoveAdminViewModel: ViewModel(), AdminRemoveModoratorVMRepConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private var repository=RemoveAdminRepository(this)
    private var mods= MutableLiveData<ArrayList<Customer>?>()
    fun getMods(): LiveData<ArrayList<Customer>?> {return mods}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.modRetrive()
    }
    override fun onModsRetrieved(list:ArrayList<Customer>?){
        if(list!=null){
            mods.value=list
        }
    }
    fun deleteMod(modId:String){
        repository.deleteMod(modId)
    }
}