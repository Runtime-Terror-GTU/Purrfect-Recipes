package com.pr.purrfectrecipes.Admin

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.pr.purrfectrecipes.AuthenticationStates
import com.pr.purrfectrecipes.Connectors.AdminAddModeratorConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.CustomerStatus
import java.util.*

class AdminAddModeratoryRepository(val connector: AdminAddModeratorConnector) {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val chars = ('a'..'Z') + ('A'..'Z') + ('0'..'9')
    private fun randomPASS(): String = List(6) { chars.random() }.joinToString("")

    fun addModeratorToDatabase(nickname: String, email:String,activity: Activity){

        usersRef.orderByChild(Constants.R_USERNAME).equalTo(nickname).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    connector.addModerator(AuthenticationStates.FAILED_REGISTER)
                    Toast.makeText(activity, "There is already a moderator with this nickname", Toast.LENGTH_SHORT).show()

                }
                else{
                    val id = UUID.randomUUID().toString()
                    val password : String = randomPASS()
                    usersRef.child(id).child(Constants.R_USERNAME).setValue(nickname)
                    usersRef.child(id).child(Constants.R_USEREMAIL).setValue(email)
                    usersRef.child(id).child(Constants.R_USERPASS).setValue(password)
                    usersRef.child(id).child(Constants.R_USERSTATUS).setValue(CustomerStatus.MODERATOR.text)
                    connector.addModerator(AuthenticationStates.MODERATOR)
                    connector.moderatorPass(password)
                    Toast.makeText(activity, "Moderator added successfully", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}