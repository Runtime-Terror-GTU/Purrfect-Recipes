package com.pr.purrfectrecipes.Moderator

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.UsersModeratorVMRepConnecter
import com.example.purrfectrecipes.Moderator.UsersModeratorRepository
import com.pr.purrfectrecipes.User.Customer

class UsersModeratorViewModel: ViewModel() , UsersModeratorVMRepConnecter
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private val repository = UsersModeratorRepository(this)
    private var users= MutableLiveData<ArrayList<Customer>?>()
    fun getUsers(): LiveData<ArrayList<Customer>?> {return users}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.userRetrive()
    }
    override fun onUsersRetrieved(list:ArrayList<Customer>?){

        if(list!=null){
            users.value=list
        }
    }
    fun deleteUser(user:Customer,activity: Activity){
        repository.deleteUser(user,activity)
    }

}