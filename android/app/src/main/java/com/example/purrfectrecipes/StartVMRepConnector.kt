package com.example.purrfectrecipes

import com.example.purrfectrecipes.User.User

interface StartVMRepConnector
{
    fun onUserRetrieved(user: User?)
    fun onAddUserReturned(state: AuthenticationStates)
}