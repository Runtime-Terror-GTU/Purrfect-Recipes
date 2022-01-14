package com.pr.purrfectrecipes

import com.pr.purrfectrecipes.User.User

interface StartVMRepConnector
{
    fun onUserRetrieved(user: User?)
    fun onAddUserReturned(state: AuthenticationStates)
}