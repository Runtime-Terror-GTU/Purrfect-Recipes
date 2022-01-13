package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.User.CustomerStatus


class GetverifiedSettingsChildfragment: Fragment(R.layout.childfragment_settings_getverified)
{

    private val viewModel: SettingsSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getVerifyView().observe(viewLifecycleOwner, {
            if(viewModel.getVerifyView().value!=null)
                super.onViewCreated(viewModel.getVerifyView().value!!, savedInstanceState)
            else
            {
                viewModel.setVerifyView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })
        val emailInput = view.findViewById<EditText>(R.id.userEmailInput)

        var userStatus= "UNVERIFIED"
        viewModel.getInputUserEmail().observe(viewLifecycleOwner, {
            if(viewModel.getInputUserEmail().value!=null)
                emailInput.setText(viewModel.getInputUserEmail().value.toString())
        })
        viewModel.getStatus().observe(viewLifecycleOwner, {
            if(viewModel.getStatus().value!=null){
                userStatus = userStatus.replace("UNVERIFIED",viewModel.getStatus().value.toString(),false)
                pageView(userStatus,view)
            }
        })


    }
    fun pageView(user_status:String,view2:View){
        val getVerified = view2.findViewById<LinearLayout>(R.id.getVerified)
        val gettingVerified = view2.findViewById<LinearLayout>(R.id.gettingVerified)
        val alreadyVerified = view2.findViewById<LinearLayout>(R.id.alreadyVerified)


        val enterVerifyButton=view2.findViewById<TextView>(R.id.getVerifyButton)
        val emailInput = view2.findViewById<EditText>(R.id.userEmailInput)
        val num = (1000..9999).shuffled().last()
        if(user_status.equals(CustomerStatus.VERIFIED.text)){
            getVerified.visibility=View.GONE
            gettingVerified.visibility=View.GONE
            alreadyVerified.visibility=View.VISIBLE
        }
        else if(user_status==CustomerStatus.PREMIUM.text){
            getVerified.visibility=View.GONE
            gettingVerified.visibility=View.GONE
            alreadyVerified.visibility=View.VISIBLE
        }
        else{ //unverified
            getVerified.visibility=View.VISIBLE
            gettingVerified.visibility=View.GONE
            alreadyVerified.visibility=View.GONE

            viewModel.getInputUserEmail().observe(viewLifecycleOwner, {
                if(viewModel.getInputUserEmail().value!=null)
                    emailInput.setText(viewModel.getInputUserEmail().value.toString())

            })

            enterVerifyButton.setOnClickListener {
                if(emailInput.text!=null){
                    var toEmailList: List<String> = listOf(emailInput.text.toString())
                    val emailSubject = "Verificiation Code"
                    val emailBody = "Your verification code is : "+ num.toString()
                    val sendVerificationMail = GMail("purrfectrecipes@gmail.com","gtubilmuh3",toEmailList,emailSubject,emailBody)
                    try {
                        sendVerificationMail.createEmailMessage()
                        sendVerificationMail.sendEmail()
                        Toast.makeText(requireActivity(), "The verificiation code is send. Please enter the code.", Toast.LENGTH_SHORT).show()
                        getVerified.visibility=View.GONE
                        gettingVerified.visibility=View.VISIBLE

                    }catch (error: Exception){
                        Toast.makeText(requireActivity(), "Something went wrong. Please try again..", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(requireActivity(), "You should enter your email. Please try again..", Toast.LENGTH_SHORT).show()
                }
            }
            val enterCodeButton=view2.findViewById<TextView>(R.id.enterCodeButton)
            val userCodeInput=view2.findViewById<TextView>(R.id.userCodeInput)

            enterCodeButton.setOnClickListener {
                if(userCodeInput.text!=null){
                    if(userCodeInput.text.toString().equals(num.toString())){
                        Toast.makeText(requireActivity(), "You are verified user now.", Toast.LENGTH_SHORT).show()
                        gettingVerified.visibility=View.GONE
                        alreadyVerified.visibility=View.VISIBLE
                        viewModel.updateUserEmail(emailInput.text.toString()) //update user email in firebase

                    }
                    else{
                        Toast.makeText(requireActivity(), "The verificiation code is wrong. Please enter the code again.", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

    }



    }