package com.pr.purrfectrecipes.Admin

import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pr.purrfectrecipes.Customer.GMail
import com.pr.purrfectrecipes.R

class AddAdminChildfragment: Fragment(R.layout.childfragment_admin_add)
{
    private val viewModel: AddAdminViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        
        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })


        val inputNickname = view.findViewById<EditText>(R.id.inputNickname)
        val inputEmail = view.findViewById<EditText>(R.id.inputEmail)
        val enterButton = view.findViewById<TextView>(R.id.enterButton)

        enterButton.setOnClickListener{

            if(inputNickname.text.isEmpty() or inputEmail.text.isEmpty() ){
                Toast.makeText(requireActivity(), "Please fill the blank.", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.addModerator(inputNickname.text.toString(),inputEmail.text.toString(),requireActivity())
                viewModel.getPasssword().observe(viewLifecycleOwner, {
                    if(viewModel.getPasssword().value!=null){
                        var toEmailList: List<String> = listOf(inputEmail.text.toString())
                        val emailSubject = "Moderator Password"
                        val emailBody = "Your password is : "+ viewModel.getPasssword().value.toString()
                        val sendVerificationMail = GMail("purrfectrecipes@gmail.com","gtubilmuh3",toEmailList,emailSubject,emailBody)
                        try {
                            sendVerificationMail.createEmailMessage()
                            sendVerificationMail.sendEmail()
                            Toast.makeText(requireActivity(), "The password is send to moderator.", Toast.LENGTH_SHORT).show()

                        }catch (error: Exception){
                            Toast.makeText(requireActivity(), "Something went wrong. Please try again.."+error, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }

        }

    }


}