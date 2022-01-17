package com.pr.purrfectrecipes.Admin

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.AuthenticationStates
import com.pr.purrfectrecipes.Connectors.AdminAddModeratorConnector

class AddAdminViewModel: ViewModel(), AdminAddModeratorConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private var repository = AdminAddModeratoryRepository(this)
    private var email = MutableLiveData<String?>()
    fun getEmail(): LiveData<String?> {return email}
    private var nickname = MutableLiveData<String?>()
    fun getNickname(): LiveData<String?> {return nickname}
    private var pass = MutableLiveData<String?>()
    fun getPasssword(): LiveData<String?> {return pass}
    private var authenticationStates = MutableLiveData<AuthenticationStates?>()
    fun getAuthenticationStates(): LiveData<AuthenticationStates?> {return authenticationStates}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    fun addModerator(moderator_nickname:String,moderator_email:String,activity: Activity){
        repository.addModeratorToDatabase(moderator_nickname,moderator_email,activity)
    }
    override fun moderatorPass(password:String){
        if(password!=null){
            pass.value=password
        }
    }
    override fun addModerator(success: AuthenticationStates){
        authenticationStates.value = success
    }
}