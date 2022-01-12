package com.example.purrfectrecipes

import com.google.firebase.database.*

class EditRecipeRepository()
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val commentsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")



}