package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.User

interface MainVMRepConnector
{
    fun onUserRetrieved(user: User?)
}