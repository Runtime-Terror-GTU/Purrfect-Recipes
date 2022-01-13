package com.example.purrfectrecipes.Connectors

import androidx.lifecycle.MutableLiveData

interface SettingsVMRepConnector {
    fun getUserEmail(email: String)
    fun getUserStatus(userStatus: String)
}