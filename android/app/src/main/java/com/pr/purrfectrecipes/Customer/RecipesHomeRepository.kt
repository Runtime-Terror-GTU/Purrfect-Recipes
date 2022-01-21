package com.pr.purrfectrecipes.Customer

import android.util.Log
import com.pr.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecipesHomeRepository(val connector: RecipesHomeVMRepConnector)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")
    private var userId:String="null"

    init{
        val retrievedID=Hawk.get<String>(Constants.LOGGEDIN_USERID)
        if(retrievedID!=null)
            userId=retrievedID
    }

    var seed = Random().nextLong()

    fun retrieveUser()
    {
        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)!=null && Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)!=CustomerStatus.MODERATOR) {
            usersRef.child(Hawk.get(Constants.LOGGEDIN_USERID))
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)==null)
                            return
                        var currentUser: Customer? = null
                        val currentUserId = Hawk.get<String>(Constants.LOGGEDIN_USERID)
                        val currentUserName = snapshot.child(Constants.R_USERNAME).value
                        val currentUserStatus = snapshot.child(Constants.R_USERSTATUS).value
                        val currentUserPic = snapshot.child(Constants.R_USERPICTURE).value
                        val currentUserEmail = snapshot.child(Constants.R_USEREMAIL).value

                        if (currentUserStatus == CustomerStatus.UNVERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.UNVERIFIED,
                                pic = currentUserPic as String
                            )
                        else if (currentUserStatus == CustomerStatus.VERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.VERIFIED,
                                pic = currentUserPic as String
                            )
                        else
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.PREMIUM,
                                pic = currentUserPic as String
                            )

                        for (pRecipe in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                            currentUser.addPurrfectedRecipe(pRecipe.key.toString())
                        connector.onUserRetrieved(currentUser)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
        else
            connector.onUserRetrieved(null)
    }

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
                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {

                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            i++
                            if(i==recipesArray.size)
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
                connector.onRecipesRetrieved(recipesArray)
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

        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.child(recipeId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    addedRecipesRef.child(recipeId).setValue(false)
                    addedRecipesRef.child(recipeId).setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun decreaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount-1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).removeValue()

        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.child(recipeId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    addedRecipesRef.child(recipeId).setValue(false)
                    addedRecipesRef.child(recipeId).setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}