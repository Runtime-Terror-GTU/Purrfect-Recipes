package com.pr.purrfectrecipes.Customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.StartActivity
import com.orhanobut.hawk.Hawk

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
    }



}