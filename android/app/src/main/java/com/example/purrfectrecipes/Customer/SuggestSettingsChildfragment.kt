package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.util.*

class SuggestSettingsChildfragment: Fragment(R.layout.childfragment_settings_suggest)
{
    private val viewModel: SettingsSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getSuggestView().observe(viewLifecycleOwner, {
            if(viewModel.getSuggestView().value!=null)
                super.onViewCreated(viewModel.getSuggestView().value!!, savedInstanceState)
            else
            {
                viewModel.setSuggestView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        var userStatus= "UNVERIFIED"
        val premiumUser = view.findViewById<LinearLayout>(R.id.premiumUser)
        val notPremiumUser = view.findViewById<LinearLayout>(R.id.notPremiumUser)
        viewModel.getStatus().observe(viewLifecycleOwner, {
            if(viewModel.getStatus().value!=null){
                userStatus = userStatus.replace("UNVERIFIED",viewModel.getStatus().value.toString(),false)
                pageView(userStatus,premiumUser,notPremiumUser)
            }
        })


    }
    fun pageView(user_status:String,premiumUser:LinearLayout,notPremiumUser:LinearLayout){
        if(user_status.equals(CustomerStatus.PREMIUM.text)){
            premiumUser.visibility=View.VISIBLE
            notPremiumUser.visibility=View.GONE

            val enterSuggestIngredientButton=view?.findViewById<TextView>(R.id.enterSuggestButton)
            val suggestedIngredient = view?.findViewById<EditText>(R.id.inputSuggestedIngredient)
            enterSuggestIngredientButton?.setOnClickListener{

                if(suggestedIngredient?.text!!.isEmpty()){
                    Toast.makeText(requireActivity(), "Your suggestion has not been received.", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.suggestedIngredients(suggestedIngredient?.text.toString(),requireActivity())
                }

            }
        }
        else { //not premium user
            premiumUser.visibility=View.GONE
            notPremiumUser.visibility=View.VISIBLE
        }
    }

}