package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.Customer

interface AdminRemoveModoratorVMRepConnector {
    fun onModsRetrieved(list:ArrayList<Customer>?)
}