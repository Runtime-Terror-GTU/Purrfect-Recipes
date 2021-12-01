package com.example.purrfectrecipes.User

import java.io.Serializable

interface User:Serializable
{
    fun getUserID():String
    fun getUsername():String
    fun getUserEmail():String
    fun getUserPassword():String
    fun changePassword(newPass:String, controlPass:String):Boolean
}