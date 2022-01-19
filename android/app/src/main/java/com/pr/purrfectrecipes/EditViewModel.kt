package com.pr.purrfectrecipes

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.EditVMRepConnector
import com.pr.purrfectrecipes.User.Customer

class EditViewModel: ViewModel(), EditVMRepConnector {
    private val username = MutableLiveData<String?>()
    fun getUserName() : LiveData<String?> {return username}

    private val repository = EditRepository(this)
    private val user = MutableLiveData<Customer?>()
    fun getUser() : LiveData<Customer?> {return user}

    var imageUri:Uri?=null

    init{
        repository.userInfoRetrive()
    }
    override fun userInfo(customer: Customer){
        user.value=customer
    }
    fun changePassword(newPass:String){
        repository.changePassword(newPass)
        if(imageUri!=null)
            repository.changePicture(imageUri!!)
    }
    fun changeUsername(newName:String,activity: Activity){
        if(newName!=null){
            repository.changeUserName(newName,activity)
        }
    }
    fun changeBio(newBio:String){
        repository.changeBio(newBio)
    }
    fun changePicture2(defaultPic:String){
        repository.changePictureDefault(defaultPic)

    }

}