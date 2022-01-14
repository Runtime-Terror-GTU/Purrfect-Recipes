package com.pr.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.MainVMRepConnector
import com.pr.purrfectrecipes.User.User

class MainViewModel: ViewModel(), MainVMRepConnector
{
    private val repository=MainRepository(this)
    private var retrievedUser= MutableLiveData<User?>()
        fun getRetrievedUser(): LiveData<User?> {return retrievedUser}

    fun logIn(userID:String)
    {
        repository.retrieveUser(userID)
    }

    override fun onUserRetrieved(user: User?) {
        retrievedUser.value=user
    }
}