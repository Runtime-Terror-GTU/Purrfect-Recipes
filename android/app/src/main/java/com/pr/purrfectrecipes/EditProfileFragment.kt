package com.pr.purrfectrecipes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Customer.InfoProfileViewModel
import com.pr.purrfectrecipes.User.CustomerStatus

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private val viewModel: EditViewModel by activityViewModels()
    private val infoProileViewModel:InfoProfileViewModel by activityViewModels()
    val PICK_IMAGE = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = view.findViewById<TextView>(R.id.username)
        val bio = view.findViewById<EditText>(R.id.bio)
        val password1 = view.findViewById<EditText>(R.id.password1)
        val password2 = view.findViewById<EditText>(R.id.password2)
        val doneButton = view.findViewById<TextView>(R.id.doneButton)
        val userProfilePic =view.findViewById<ImageView>(R.id.userProfilePic)
        val userStatus = Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)
        val cancelButton=view.findViewById<ImageView>(R.id.cancel)

        viewModel.getUser().observe(viewLifecycleOwner,{
            if(viewModel.getUser().value!=null) {
                val user = viewModel.getUser().value
                bio.setText(user?.getUserBio()!!)
                username.text=user.getUsername()
                password1.setText(user.getUserPassword())
                password2.setText(user.getUserPassword())
                Glide.with(requireContext())
                    .load(user.getUserPic())
                    .into(userProfilePic)
            }
        })

            userProfilePic.setOnClickListener {
                if(userStatus==CustomerStatus.PREMIUM) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE
                    )
                }
                else{
                    Toast.makeText(requireActivity(), "You should be premium user.", Toast.LENGTH_SHORT).show()

                }
            }


        doneButton.setOnClickListener{
            if(username.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Please enter username correctly first.", Toast.LENGTH_SHORT).show()
            else if(password1.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Please enter the password twice correctly", Toast.LENGTH_SHORT).show()
            else if(password1.text!=null){
                if(password2.text!=null){
                    val pass1=password1.text.toString()
                    val pass2=password2.text.toString()
                    if(pass1.equals(pass2)){
                        viewModel.changePassword(password1.text.toString())
                        Toast.makeText(requireActivity(), "Your password changed.", Toast.LENGTH_SHORT).show()
                        infoProileViewModel.setEditProfile(false)
                    }
                    else{
                        Toast.makeText(requireActivity(), "Please enter the password twice correctly", Toast.LENGTH_SHORT).show()

                    }
                }
            }
            else if(!username.text.isNullOrEmpty()){
                viewModel.changeUsername(username.text.toString(),requireActivity())
            }
            viewModel.changeBio(bio.text.toString())
        }

        cancelButton.setOnClickListener {
            infoProileViewModel.setEditProfile(false)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val userProfilePic =view?.findViewById<ImageView>(R.id.userProfilePic)
        if (requestCode == PICK_IMAGE) {
            val selectedImageUri: Uri? = data?.getData()
            userProfilePic?.setImageURI(selectedImageUri)
            viewModel.imageUri=(selectedImageUri)
        }
    }
}