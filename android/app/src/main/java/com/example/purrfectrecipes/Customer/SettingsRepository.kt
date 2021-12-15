package com.example.purrfectrecipes.Customer

import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Constants
import com.orhanobut.hawk.Hawk
import androidx.activity.viewModels
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.example.purrfectrecipes.MainVMRepConnector
import com.example.purrfectrecipes.R
import com.google.firebase.database.*

class SettingsRepository(val connector: SettingsVMRepConnector){
    private val userID= Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    fun findUserEmail(){

        usersRef.child(userID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var email = snapshot.child(Constants.R_USEREMAIL).value.toString()
                connector.getUserEmail(email)
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}