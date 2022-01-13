package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.User.CustomerStatus
import com.orhanobut.hawk.Hawk

class ShopSettingsChildFragment: Fragment(R.layout.childfragment_settings_shop)
{
    private val viewModel: SettingsSharedViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getShopView().observe(viewLifecycleOwner, {
            if(viewModel.getShopView().value!=null)
                super.onViewCreated(viewModel.getShopView().value!!, savedInstanceState)
            else
            {
                viewModel.setShopView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })
        var userStatus= "UNVERIFIED"
        val getInput = view.findViewById<LinearLayout>(R.id.getInput)
        val enterButton = view.findViewById<LinearLayout>(R.id.enterButton)
        val notVerifiedUser = view.findViewById<LinearLayout>(R.id.notVerifiedUser)
        val PremiumUser = view.findViewById<LinearLayout>(R.id.PremiumUser)

        viewModel.getStatus().observe(viewLifecycleOwner, {
            if(viewModel.getStatus().value!=null){
                userStatus = userStatus.replace("UNVERIFIED",viewModel.getStatus().value.toString(),false)
                pageView(userStatus,PremiumUser,notVerifiedUser,getInput,enterButton)
            }
        })




    }
    fun pageView(user_status:String,PremiumUser:LinearLayout,notVerifiedUser:LinearLayout,getInput: LinearLayout,enterButton:LinearLayout){
        if(user_status.equals(CustomerStatus.VERIFIED.text)){
            getInput.visibility=View.VISIBLE
            enterButton.visibility=View.VISIBLE
            notVerifiedUser.visibility=View.GONE
            PremiumUser.visibility=View.GONE
        }
        else if(user_status.equals(CustomerStatus.PREMIUM.text)){
            getInput.visibility=View.GONE
            enterButton.visibility=View.GONE
            notVerifiedUser.visibility=View.GONE
            PremiumUser.visibility=View.VISIBLE
        }
        else {
            getInput.visibility=View.GONE
            enterButton.visibility=View.GONE
            notVerifiedUser.visibility=View.VISIBLE
            PremiumUser.visibility=View.GONE
        }
    }

}