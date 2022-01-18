package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.User.Customer

interface EditVMRepConnector {
    fun userInfo(customer: Customer)
}