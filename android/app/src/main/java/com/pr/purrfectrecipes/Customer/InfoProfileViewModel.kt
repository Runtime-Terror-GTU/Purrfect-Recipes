package com.pr.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.InfoProfileConnector

class InfoProfileViewModel: ViewModel(), InfoProfileConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private var username = MutableLiveData<String?>()
    fun getUsername(): LiveData<String?> {return username}
    private var user_bio =  MutableLiveData<String?>()
    fun getBio(): LiveData<String?> {return user_bio}
    private var addedRecipeNum =  MutableLiveData<Int?>()
    fun getAddedRecipeNum(): LiveData<Int?> {return addedRecipeNum}
    private val repository = InfoProfileRepository(this)
    private var user_pic =  MutableLiveData<String?>()
    fun getPicture(): LiveData<String?> {return user_pic}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.getUserName()
        repository.getUserBio()
        repository.getUserAddedRecipeNum()
        repository.getUserPic()
    }
    override fun getName(name:String){
        if(name!=null){
            username.value = name
        }
    }
    override fun getBio(bio:String){
        if(bio!=null){
            user_bio.value = bio
        }
    }
    override fun getAddedRecipeNum(num:Int){
        if(num!=null){
            addedRecipeNum.value=num
        }
    }
    override fun getProfilePic(pictureUrl:String){
        if(pictureUrl!=null){
            user_pic.value=pictureUrl
        }
    }
}