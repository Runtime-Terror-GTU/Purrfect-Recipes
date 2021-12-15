package com.example.purrfectrecipes.Customer

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.StartRepository
import com.example.purrfectrecipes.User.User

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
    }
    override fun getUserEmail(email:String)  {

        inputUserEmail.value = email
        println(inputUserEmail.value.toString()+"...") //mail görünüyor burada.

    }


}