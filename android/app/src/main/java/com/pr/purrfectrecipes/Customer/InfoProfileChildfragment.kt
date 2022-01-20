package com.pr.purrfectrecipes.Customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.EditViewModel
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.StartActivity
import com.pr.purrfectrecipes.User.CustomerStatus

class InfoProfileChildfragment: Fragment(R.layout.childfragment_profile_info)
{
    private val viewModel: InfoProfileViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val progressBar=view.findViewById<ProgressBar>(R.id.loadingBar)
        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        val logoutButton=view.findViewById<ImageView>(R.id.logoutButton)
        logoutButton?.setOnClickListener {
            Hawk.delete(Constants.LOGGEDIN_USERID)
            Hawk.delete(Constants.LOGGEDIN_USER_STATUS)

            val intent= Intent(context, StartActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent)
            parentFragment?.activity?.finish()
        }

        val userStatus = Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS).text
        val premiumSymbol = view.findViewById<ImageView>(R.id.premiumSymbol)
        val username = view.findViewById<TextView>(R.id.username)
        val userBio = view.findViewById<TextView>(R.id.userBio)
        val addedRecipesCount = view.findViewById<TextView>(R.id.addedRecipesCount)
        val profilePic = view.findViewById<ImageView>(R.id.profilePic)
        if(!userStatus.equals(CustomerStatus.PREMIUM.text)){
            premiumSymbol.visibility=View.GONE
        }
        viewModel.getUser().observe(viewLifecycleOwner, {
            if(viewModel.getUser().value!=null)
            {
                username.setText(viewModel.getUser().value!!.getUsername())
                userBio.text=viewModel.getUser().value!!.getUserBio()
                addedRecipesCount.setText(viewModel.getUser().value!!.getAddedRecipes().size.toString()+" Added Recipes")
                Glide.with(requireContext())
                    .load(viewModel.getUser().value!!.getUserPic())
                    .into(profilePic)

                progressBar.visibility=View.GONE
            }
        })

        val editProfileButton = view.findViewById<ImageView>(R.id.editProfileButton)
        editProfileButton?.setOnClickListener {
            viewModel.setEditProfile(true)

        }


    }

}