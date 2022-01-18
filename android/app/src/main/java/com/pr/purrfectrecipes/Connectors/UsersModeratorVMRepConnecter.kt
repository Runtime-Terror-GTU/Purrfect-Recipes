package com.example.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.Customer


interface UsersModeratorVMRepConnecter {
    fun onUsersRetrieved(list:ArrayList<Customer>?)
}