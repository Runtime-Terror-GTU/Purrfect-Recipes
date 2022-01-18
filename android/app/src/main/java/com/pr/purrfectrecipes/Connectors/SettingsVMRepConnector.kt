package com.pr.purrfectrecipes.Connectors

interface SettingsVMRepConnector {
    fun getUserEmail(email: String)
    fun getUserStatus(userStatus: String)
    fun getMostLike(likeNum:Int)
}