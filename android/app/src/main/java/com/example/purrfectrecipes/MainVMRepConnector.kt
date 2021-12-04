package com.example.purrfectrecipes

import com.example.purrfectrecipes.User.User

interface MainVMRepConnector
{
    fun onUserRetrieved(user: User?)
}