package com.pr.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.User.Admin
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.Moderator
import com.pr.purrfectrecipes.User.User

class StartViewModel:ViewModel(),StartVMRepConnector
{
    private val repository=StartRepository(this)
    private var retrievedUser:User?=null
        fun getRetrievedUser(): User? {return retrievedUser}
    private var authenticationState=MutableLiveData<AuthenticationStates>()
        fun getAuthenticationState():LiveData<AuthenticationStates>{return authenticationState}

    fun logIn(username:String, password:String)
    {
        repository.retrieveUser(username, password)
    }

    fun register(user:User)
    {
        repository.addUser(user as Customer)
    }

    override fun onUserRetrieved(user: User?) {
        retrievedUser=user
        if(retrievedUser is Admin)
            authenticationState.value=AuthenticationStates.ADMIN
        else if(retrievedUser is Moderator)
            authenticationState.value=AuthenticationStates.MODERATOR
        else if(retrievedUser is Customer)
            authenticationState.value=AuthenticationStates.CUSTOMER
        else
            authenticationState.value=AuthenticationStates.FAILED_LOGIN
    }

    override fun onAddUserReturned(state: AuthenticationStates) {
        authenticationState.value= AuthenticationStates.FAILED_REGISTER
    }

}