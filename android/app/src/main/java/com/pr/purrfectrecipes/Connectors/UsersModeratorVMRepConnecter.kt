package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.User.UserClass

interface UsersModeratorVMRepConnecter {
    fun onUsersRetrieved(list:ArrayList<UserClass>?)
}