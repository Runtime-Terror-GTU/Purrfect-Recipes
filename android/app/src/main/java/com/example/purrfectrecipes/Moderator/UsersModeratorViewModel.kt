package com.example.purrfectrecipes.Moderator

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.UsersModeratorVMRepConnecter
import com.example.purrfectrecipes.User.UserClass

class UsersModeratorViewModel: ViewModel() , UsersModeratorVMRepConnecter
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private val repository = UsersModeratorRepository(this)
    private var users= MutableLiveData<ArrayList<UserClass>?>()
    fun getUsers(): LiveData<ArrayList<UserClass>?> {return users}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.userRetrive()
    }
    override fun onUsersRetrieved(list:ArrayList<UserClass>?){

        if(list!=null){
            users.value=list
        }
    }
    fun deleteUser(userID:String,activity: Activity){
        repository.deleteUser(userID,activity)
    }

}