package com.example.purrfectrecipes.Customer

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.orhanobut.hawk.Hawk
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

    var seed = Random().nextLong()

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
                    for(tag in ds.child(Constants.R_RECIPETAGS).children)
                        recipe.addTag(tag.key.toString())

                    recipesArray.add(recipe)
                }

                var i=0
                var owner: Customer?=null
                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(owner==null && Hawk.get<String>(Constants.LOGGEDIN_USERID)==recipe.recipeOwner)
                            {
                                val ownerId=recipe.recipeOwner
                                val ownerName=snapshot.child(Constants.R_USERNAME).value
                                val ownerStatus=snapshot.child(Constants.R_USERSTATUS).value
                                val ownerPic=snapshot.child(Constants.R_USERPICTURE).value
                                val ownerEmail=snapshot.child(Constants.R_USEREMAIL).value

                                if(ownerStatus== CustomerStatus.UNVERIFIED.text)
                                    owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.UNVERIFIED, pic=ownerPic as String)
                                else if(ownerStatus== CustomerStatus.VERIFIED.text)
                                    owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.VERIFIED, pic=ownerPic as String)
                                else
                                    owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.PREMIUM, pic=ownerPic as String)

                                for(pRecipe in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                                    owner!!.addPurrfectedRecipe(pRecipe.key.toString())
                            }

                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            i++
                            if(i==recipesArray.size)
                                getRecipeOfTheDay(recipesArray, owner)
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

    fun getRecipeOfTheDay(recipesArray:ArrayList<Recipe>, owner:Customer?)
    {
        dayRecipeRef.addListenerForSingleValueEvent(object: ValueEventListener{
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

                recipesArray.shuffle(Random(seed))
                connector.onRecipesRetrieved(recipesArray, owner)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun increaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount+1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).setValue(true)
    }

    fun decreaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount-1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).removeValue()
    }
}