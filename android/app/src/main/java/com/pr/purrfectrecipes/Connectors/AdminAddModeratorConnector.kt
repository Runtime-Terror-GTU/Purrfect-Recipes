package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.AuthenticationStates

interface AdminAddModeratorConnector {
    fun moderatorPass(password:String)
    fun addModerator(success: AuthenticationStates)
}