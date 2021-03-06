package com.pr.purrfectrecipes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color.Companion.Transparent
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

        val username = view.findViewById<EditText>(R.id.username)
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
                username.setText(user.getUsername())
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
                    showPictures()
                }
            }


        doneButton.setOnClickListener{
            if(username.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Please enter username correctly first.", Toast.LENGTH_SHORT).show()
            else if(!username.text.isNullOrEmpty()){
                viewModel.changeUsername(username.text.toString(),requireActivity())
                infoProileViewModel.setEditProfile(false)
            }
            if(password1.text.isNullOrEmpty())
                Toast.makeText(requireActivity(), "Please enter the password twice correctly", Toast.LENGTH_SHORT).show()
            else if(password1.text!=null && !password1.text.toString().equals("*******")){
                if(password2.text!=null && !password2.text.toString().equals("*******")){
                    val pass1=password1.text.toString()
                    val pass2=password2.text.toString()
                    if(pass1.equals(pass2)){
                        viewModel.changePassword(password1.text.toString())
                        Toast.makeText(requireActivity(), "Your info is updated.", Toast.LENGTH_SHORT).show()
                        infoProileViewModel.setEditProfile(false)
                    }
                    else{
                        Toast.makeText(requireActivity(), "Please enter the password twice correctly", Toast.LENGTH_SHORT).show()

                    }
                }
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
    fun showPictures(){

        val dialog = Dialog(requireContext())
        dialog?.setContentView(R.layout.select_picture_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()

        //show dialog

        val default_pic1 = dialog?.findViewById<ImageView>(R.id.default_pic1)
        val default_pic2 = dialog?.findViewById<ImageView>(R.id.default_pic2)
        val default_pic3 = dialog?.findViewById<ImageView>(R.id.default_pic3)
        val default_pic4 = dialog?.findViewById<ImageView>(R.id.default_pic4)
        val default_pic5 = dialog?.findViewById<ImageView>(R.id.default_pic5)
        val default_pic6 = dialog?.findViewById<ImageView>(R.id.default_pic6)
        val default_pic7 = dialog?.findViewById<ImageView>(R.id.default_pic7)
        val default_pic8 = dialog?.findViewById<ImageView>(R.id.default_pic8)
        val default_pic9 = dialog?.findViewById<ImageView>(R.id.default_pic9)
        val default_pic10 = dialog?.findViewById<ImageView>(R.id.default_pic10)
        val default_pic11 = dialog?.findViewById<ImageView>(R.id.default_pic11)
        val default_pic12 = dialog?.findViewById<ImageView>(R.id.default_pic12)


        default_pic1.setOnClickListener {
            viewModel.changePicture2("default_pic1")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic2.setOnClickListener {
            viewModel.changePicture2("default_pic2")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic3.setOnClickListener {
            viewModel.changePicture2("default_pic3")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic4.setOnClickListener {
            viewModel.changePicture2("default_pic4")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic5.setOnClickListener {
            viewModel.changePicture2("default_pic5")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic6.setOnClickListener {
            viewModel.changePicture2("default_pic6")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic7.setOnClickListener {
            viewModel.changePicture2("default_pic7")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic8.setOnClickListener {
            viewModel.changePicture2("default_pic8")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic9.setOnClickListener {
            viewModel.changePicture2("default_pic9")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic10.setOnClickListener {
            viewModel.changePicture2("default_pic10")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic11.setOnClickListener {
            viewModel.changePicture2("default_pic11")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }
        default_pic12.setOnClickListener {
            viewModel.changePicture2("default_pic12")
            dialog?.dismiss()
            Toast.makeText(requireActivity(), "Your picture is changing..", Toast.LENGTH_SHORT).show()

        }

    }
}