package com.pr.purrfectrecipes

import com.pr.purrfectrecipes.Connectors.FilterVMRepConnector
import com.google.firebase.database.*

class FilterRepository(val connector: FilterVMRepConnector)
{
    private val tagsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tags")

    fun retrieveTags()
    {
        tagsRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val tags=ArrayList<String>()
                for(ds in snapshot.children)
                {
                    val tagName=ds.key.toString()
                    tags.add(tagName)
                }
                connector.onTagsRetrieved(tags)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}