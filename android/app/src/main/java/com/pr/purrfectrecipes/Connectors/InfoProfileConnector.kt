package com.pr.purrfectrecipes.Connectors

interface InfoProfileConnector {
    fun getName(name:String)
    fun getBio(bio:String)
    fun getAddedRecipeNum(num:Int)
    fun getProfilePic(pictureUrl:String)
}