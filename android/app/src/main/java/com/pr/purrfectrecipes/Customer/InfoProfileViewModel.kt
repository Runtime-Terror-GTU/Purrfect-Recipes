package com.pr.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.InfoProfileConnector
import com.pr.purrfectrecipes.User.Customer

class InfoProfileViewModel: ViewModel(), InfoProfileConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}

    private val repository = InfoProfileRepository(this)

    private var user=MutableLiveData<Customer>()
        fun getUser():LiveData<Customer>{return user}

    private val editProfile=MutableLiveData<Boolean?>()
        fun getEditProfile():LiveData<Boolean?>{return editProfile}

    fun setView(newView: View?)
    {
        view.value=newView
    }

    fun setEditProfile(bool:Boolean?)
    {
        editProfile.value=bool
    }

    init{
        repository.retrieveUser()
    }

    override fun onUserRetrieved(user: Customer) {
        this.user.value=user
    }



}