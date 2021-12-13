package com.example.purrfectrecipes.Customer

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt

class RecipesHomeRepository(val connector: RecipesHomeVMRepConnector)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")
    private val picturesStorageRef=FirebaseStorage.getInstance().getReference().child("Recipe Pictures")

    fun retrieveRecipes()
    {
        recipesRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipesArray=ArrayList<Recipe>()
                for(ds in snapshot.children)
                {
                    val id=ds.key.toString()
                    val name=ds.child(Constants.R_RECIPENAME).value.toString()
                    val ownerId=ds.child(Constants.R_RECIPEOWNER).value.toString()
                    val difficulty=ds.child(Constants.R_RECIPEDIFFICULTY).value.toString()
                    val likes=ds.child(Constants.R_RECIPEPURRFECTEDCOUNT).value.toString()
                    val pictureUrl=ds.child(Constants.R_RECIPEPICTURE).value.toString()

                    val recipe=Recipe(id, name, ownerId, difficulty, likes.toInt(), pictureUrl)
                    recipesArray.add(recipe)
                }

                var i=0
                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            i++
                            if(i==recipesArray.size-1)
                                getRecipeOfTheDay(recipesArray)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }

    fun getRecipeOfTheDay(recipesArray:ArrayList<Recipe>)
    {
        dayRecipeRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val format: DateFormat = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
                val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
                val c2: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3"))
                c.set(Calendar.HOUR_OF_DAY, 0)
                c.set(Calendar.MINUTE, 0)
                c.set(Calendar.SECOND, 0)
                c.set(Calendar.MILLISECOND, 0)
                for(ds in snapshot.children) {
                    val date: Date? = format.parse(ds.key.toString())
                    c2.time=date
                    c2.set(Calendar.HOUR_OF_DAY, 0)
                    c2.set(Calendar.MINUTE, 0)
                    c2.set(Calendar.SECOND, 0)
                    c2.set(Calendar.MILLISECOND, 0)
                    if(c2.compareTo(c)!=0) {
                        val min = 0;
                        val max = recipesArray.size-1;
                        val random = Random().nextInt((max - min) + 1) + min
                        connector.onSelectRecipeOfTheDay(recipesArray.get(random))
                        ds.ref.removeValue()
                        dayRecipeRef.child(""+c.time.date.toString()+" "+(c.time.month+1).toString()+" "+(c.time.year+1900).toString()).setValue(recipesArray.get(random).getRecipeID())
                    }
                    else if(c2.compareTo(c)==0)
                    {
                        for(recipe in recipesArray)
                            if(recipe.getRecipeID()==ds.value)
                                connector.onSelectRecipeOfTheDay(recipe)
                    }
                }
                recipesArray.shuffle()
                connector.onRecipesRetrieved(recipesArray)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}