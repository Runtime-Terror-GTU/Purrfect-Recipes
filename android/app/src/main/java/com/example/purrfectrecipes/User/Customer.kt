package com.example.purrfectrecipes.User

class Customer(id:String, username:String, email:String, password:String, status:CustomerStatus=CustomerStatus.UNVERIFIED, bio:String="Insert Bio Here", pic:String="Insert Picture URL here")
    :AbstractUser(id, username, email, password)
{
    private var userStatus:CustomerStatus=status
        fun getUserStatus():CustomerStatus{return userStatus}
        fun changeUserStatus(newStatus:CustomerStatus)
        {
            if((userStatus==CustomerStatus.VERIFIED || userStatus==CustomerStatus.VERIFIED) && newStatus==CustomerStatus.UNVERIFIED)
                return
            else
                userStatus=newStatus
        }
    private var userBio:String=bio
        fun getUserBio():String{return userBio}
    private var userPic:String=pic
        fun getUserPic():String{return userPic}

    private var addedRecipes:HashSet<String>?=null
    private var purrfectedRecipes=HashSet<String>()

    init{
        if(getUserStatus()!=CustomerStatus.UNVERIFIED)
            addedRecipes=HashSet()
    }

    fun isAddedRecipe(element:String):Boolean{
        if(addedRecipes==null)
            return false
        else
            return addedRecipes?.contains(element)!!
    }

    fun isPurrfectedRecipe(recipeID:String):Boolean
    {
        return purrfectedRecipes.contains(recipeID)
    }

    fun addAddedRecipe(recipeID:String)
    {
        if(getUserStatus()!=CustomerStatus.UNVERIFIED)
            addedRecipes?.add(recipeID)
    }

    fun addPurrfectedRecipe(recipeID:String)
    {
        purrfectedRecipes.add(recipeID)
    }

    fun removeAddedRecipe(recipeID:String)
    {
        addedRecipes?.remove(recipeID)
    }

    fun removePurrfectedRecipe(recipeID: String)
    {
        purrfectedRecipes.remove(recipeID)
    }
}