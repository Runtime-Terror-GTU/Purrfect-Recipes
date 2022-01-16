package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.Customer

interface InfoProfileConnector {
    fun onUserRetrieved(user: Customer)
}