package com.pr.purrfectrecipes.Admin


import android.util.Log
import com.google.firebase.database.*
import com.pr.purrfectrecipes.Connectors.AdminRemoveModoratorVMRepConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus

class RemoveAdminRepository(val connector: AdminRemoveModoratorVMRepConnector) {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val mods = ArrayList<Customer>()
    fun modRetrive(){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mods.clear()
                for(ds in snapshot.children){
                    val status= ds.child(Constants.R_USERSTATUS).value.toString()
                    val currentUserPic ="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic1.png?alt=media&token=04ac54da-978e-43a6-8fe5-5dbdd2267da3"
                    Log.i("here", currentUserPic.toString())
                    if(status.equals(CustomerStatus.MODERATOR.text)){
                        val mod = Customer(ds.key.toString(), ds.child(Constants.R_USERNAME).value.toString(),"..","123456",CustomerStatus.MODERATOR,"-",currentUserPic)
                        mods.add(mod)
                    }
                }
                connector.onModsRetrieved(mods)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun deleteMod(modId:String){
        usersRef.child(modId).removeValue()
    }
}