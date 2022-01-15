package com.example.purrfectrecipes.User

class UserClass(id:String, username:String,status:String,picture:String,recipeNum:Int) {
    private val userID:String=id
     fun getUserID(): String {return userID}
    private var username:String=username
     fun getUsername(): String {return username}
    private var userStatus:String=status
    fun getUserStatus(): String {return userStatus}
    private var userPic:String=picture
    fun getUserPic(): String {return userPic}
    private var addedRecipeNum:Int=recipeNum
    fun getAddedRecipeNum(): Int {return addedRecipeNum}
    fun setAddedRecipeNum(num:Int){
        addedRecipeNum = num
    }

    override fun equals(other: Any?): Boolean {
        if(other !is User)
            return false
        else
            return userID.equals(other.getUserID())
    }

}