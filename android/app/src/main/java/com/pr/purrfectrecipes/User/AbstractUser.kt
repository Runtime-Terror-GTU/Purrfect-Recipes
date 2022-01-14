package com.pr.purrfectrecipes.User

abstract class AbstractUser(id:String, username:String, email:String, password:String="12345"):User
{
    private val userID:String=id
        override fun getUserID(): String {return userID}
    private var username:String=username
        override fun getUsername(): String {return username}
    private var userEmail:String=email
        override fun getUserEmail(): String {return userEmail}
    private var userPassword:String=password
        override fun getUserPassword(): String {return String(userPassword.toCharArray())}
        override fun changePassword(newPass: String, controlPass: String): Boolean {
            if(newPass.equals(controlPass))
            {
                userPassword=newPass
                return true
            }
            else
                return false
        }

    override fun equals(other: Any?): Boolean {
        if(other !is User)
            return false
        else
            return userID.equals(other.getUserID())
    }

}