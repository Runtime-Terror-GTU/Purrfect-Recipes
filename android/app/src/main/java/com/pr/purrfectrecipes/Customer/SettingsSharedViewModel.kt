package com.pr.purrfectrecipes.Customer

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.SettingsVMRepConnector

class SettingsSharedViewModel: ViewModel(), SettingsVMRepConnector
{
    private val repository= SettingsRepository(this)
    private var inputUserEmail= MutableLiveData<String?>()
    fun getInputUserEmail(): LiveData<String?> {return inputUserEmail}
    private var verifyView= MutableLiveData<View?>()
    fun getVerifyView(): LiveData<View?> {return verifyView}
    private var shopView= MutableLiveData<View?>()
    fun getShopView(): LiveData<View?> {return shopView}
    private var suggestView= MutableLiveData<View?>()
    fun getSuggestView(): LiveData<View?> {return suggestView}
    private var status= MutableLiveData<String?>()
    fun getStatus(): LiveData<String?> {return status}

    private val getVerified=MutableLiveData<Boolean?>()
        fun getGetVerified():LiveData<Boolean?>{return getVerified}
    private val buyPremium=MutableLiveData<Boolean?>()
        fun getBuyPremium():LiveData<Boolean?>{return buyPremium}

    fun setGetVerified(bool:Boolean?)
    {
        getVerified.value=bool
    }

    fun setBuyPremium(bool:Boolean?)
    {
        buyPremium.value=bool
    }

    fun setVerifyView(newView: View?)
    {
        verifyView.value=newView
    }

    fun setShopView(newView: View?)
    {
        shopView.value=newView
    }

    fun setSuggestView(newView: View?)
    {
        suggestView.value=newView
    }
    init{
        repository.findUserEmail()
        repository.findUserStatus()
    }
    override fun getUserEmail(email:String)  {

        inputUserEmail.value = email

    }
    fun suggestedIngredients(suggestedIngredient:String , activity: Activity){
        repository.suggestedIngredients(suggestedIngredient,activity)
    }
    override fun getUserStatus(userStatus: String){
        status.value=userStatus
    }
    fun updateUserEmail(Email: String){
        repository.updateUserEmail(Email)
    }


}