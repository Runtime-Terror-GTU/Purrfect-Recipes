package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.User.User

class MainViewModel: ViewModel(), MainVMRepConnector
{
    private val repository=MainRepository(this)
    private var retrievedUser= MutableLiveData<User>()
        fun getRetrievedUser(): LiveData<User> {return retrievedUser}

    fun logIn(username:String, password:String)
    {
        repository.retrieveUser(username, password)
    }

    override fun onUserRetrieved(user: User?) {
        if(user!=null)
            retrievedUser.value=user!!
    }
}