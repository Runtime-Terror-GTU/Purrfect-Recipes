package com.example.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.Customer

interface UsersDeleteOnClickListener {
    fun onDeleteClick(user: Customer)
}