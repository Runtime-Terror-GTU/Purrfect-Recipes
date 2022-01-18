package com.pr.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.CustomerStatus

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

        val notVerifiedUser = view.findViewById<LinearLayout>(R.id.notVerifiedUser)
        val PremiumUser = view.findViewById<LinearLayout>(R.id.PremiumUser)
        val mostLikeNum = view.findViewById<TextView>(R.id.mostLikeNum)

        viewModel.getStatus().observe(viewLifecycleOwner, {
            if(viewModel.getStatus().value!=null){
                userStatus = userStatus.replace("UNVERIFIED",viewModel.getStatus().value.toString(),false)
                pageView(userStatus,PremiumUser,notVerifiedUser,mostLikeNum)
            }
        })

        val beVerifiedButton=view.findViewById<LinearLayout>(R.id.beVerifiedButton)
        beVerifiedButton.setOnClickListener {
            viewModel.setGetVerified(true)
        }

    }
    fun pageView(user_status:String,PremiumUser:LinearLayout,notVerifiedUser:LinearLayout,mostLikeNum:TextView){
        if(user_status.equals(CustomerStatus.VERIFIED.text)){
            notVerifiedUser.visibility=View.GONE
            PremiumUser.visibility=View.GONE
            viewModel.getMostLike().observe(viewLifecycleOwner, {
                if(viewModel.getMostLike().value!=null){
                    mostLikeNum.setText("Your most get liked recipe likes is: "+viewModel.getMostLike().value.toString())
                }
            })

        }
        else if(user_status.equals(CustomerStatus.PREMIUM.text)){
            notVerifiedUser.visibility=View.GONE
            PremiumUser.visibility=View.VISIBLE
            mostLikeNum.visibility=View.GONE
        }
        else {
            notVerifiedUser.visibility=View.VISIBLE
            PremiumUser.visibility=View.GONE
            mostLikeNum.visibility=View.GONE
        }
    }

}