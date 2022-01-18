package com.pr.purrfectrecipes.Admin


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
                for(ds in snapshot.children){
                    val status= ds.child(Constants.R_USERSTATUS).value.toString()
                    if(status.equals(CustomerStatus.MODERATOR.text)){
                        val mod = Customer(ds.key.toString(), ds.child(Constants.R_USERNAME).value.toString(),"..","123456",CustomerStatus.MODERATOR,"-","pic")
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