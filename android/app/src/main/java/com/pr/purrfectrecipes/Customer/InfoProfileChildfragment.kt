package com.pr.purrfectrecipes.Customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.StartActivity
import com.pr.purrfectrecipes.User.CustomerStatus

class InfoProfileChildfragment: Fragment(R.layout.childfragment_profile_info)
{
    private val viewModel: InfoProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        val userStatus = Hawk.get<String>(Constants.LOGGEDIN_USER_STATUS)
        val premiumSymbol = view.findViewById<ImageView>(R.id.premiumSymbol)
        val username = view.findViewById<TextView>(R.id.username)
        val userBio = view.findViewById<TextView>(R.id.userBio)
        val addedRecipesCount = view.findViewById<TextView>(R.id.addedRecipesCount)
        val profilePic = view.findViewById<ImageView>(R.id.profilePic)
        if(!userStatus.equals(CustomerStatus.PREMIUM.text)){
            premiumSymbol.visibility=View.GONE
        }
        viewModel.getUsername().observe(viewLifecycleOwner, {
            if(viewModel.getUsername().value!=null){
                username.setText(viewModel.getUsername().value.toString())
            }
        })
        viewModel.getBio().observe(viewLifecycleOwner, {
            if(viewModel.getBio().value!=null){
                userBio.setText(viewModel.getBio().value.toString())
            }
        })
        viewModel.getAddedRecipeNum().observe(viewLifecycleOwner, {
            if(viewModel.getAddedRecipeNum().value!=null){
                addedRecipesCount.setText(viewModel.getAddedRecipeNum().value.toString()+" Added Recipes")
            }
        })
        viewModel.getPicture().observe(viewLifecycleOwner, {
            if(viewModel.getPicture().value!=null){
               val storageRef= FirebaseStorage.getInstance().getReference().child("User Pictures")
                val pic = storageRef.child(viewModel.getPicture().value.toString())

                // Load the image using Glide 
                Glide.with(this /* context */)
                    .load(pic)
                    .into(profilePic );
            }
        })



    }



}