package com.pr.purrfectrecipes

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.EditVMRepConnector
import com.pr.purrfectrecipes.User.Customer

class EditViewModel: ViewModel(), EditVMRepConnector {
    private val username = MutableLiveData<String?>()
    fun getUserName() : LiveData<String?> {return username}
    private val password = MutableLiveData<String?>()
    fun getPass() : LiveData<String?> {return password}
    private val bio = MutableLiveData<String?>()
    fun getBio() : LiveData<String?> {return bio}
    private val picture = MutableLiveData<String?>()
    fun getPicture() : LiveData<String?> {return picture}
    private val repository = EditRepository(this)
    private val user = MutableLiveData<Customer?>()
    fun getUser() : LiveData<Customer?> {return user}

    init{
        repository.userInfoRetrive()
    }
    override fun userInfo(customer: Customer){
        if(customer.getUsername()!=null){
            username.value=customer.getUsername()
        }
        if(customer.getUserBio()!=null){
            bio.value=customer.getUserBio()
        }
        if(customer.getUserPic()!=null){
            picture.value=customer.getUserPic()
        }
    }
    fun changePassword(newPass:String){
        repository.changePassword(newPass)
    }
    fun changePicture(newPic: Uri){
        picture.value=newPic.toString()
        repository.changePicture(newPic)
    }

}