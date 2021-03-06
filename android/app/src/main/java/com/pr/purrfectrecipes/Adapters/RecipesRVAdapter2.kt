package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr.purrfectrecipes.Connectors.RecipeOnClickListener2
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer

class RecipesRVAdapter2(val context: Context, val listener: RecipeOnClickListener2): RecyclerView.Adapter<RecipesRVAdapter2.ViewHolder>() {

    private var recipes=ArrayList<Recipe>()
    private var user: Customer?=null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipePic=view.findViewById<ImageView>(R.id.recipePic)
        val purrfectButton=view.findViewById<CardView>(R.id.purrfectButton)
        val recipeName=view.findViewById<TextView>(R.id.recipeName)
        val recipeOwner=view.findViewById<TextView>(R.id.recipeOwner)
        val recipeDifficulty=view.findViewById<TextView>(R.id.recipeDifficulty)
        val recipeTag=view.findViewById<TextView>(R.id.recipeType)
        val recipeLikes=view.findViewById<TextView>(R.id.recipeLikes)
        val deleteRecipeButton=view.findViewById<ImageView>(R.id.deleteRecipeButton)
        val editRecipeButton=view.findViewById<ImageView>(R.id.editRecipeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipes_rv_bg2, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(recipes.get(position).recipePictureURL!=" ")
        {
            Glide.with(context)
                .load(recipes.get(position).recipePictureURL)
                .into(holder.recipePic)
        }
        holder.recipeName.text=recipes.get(position).recipeName
        holder.recipeOwner.text=recipes.get(position).recipeOwner
        holder.recipeDifficulty.text=recipes.get(position).recipeDifficulty
        holder.recipeTag.text=recipes.get(position).getRecipeTags().get(0)
        holder.recipeLikes.text=recipes.get(position).recipeLikes.toString()

        holder.recipePic.setOnClickListener {
            listener.onRecipeClick(recipes.get(position).getRecipeID())
        }

        holder.purrfectButton.setOnClickListener {
            if (!user!!.isPurrfectedRecipe(recipes.get(position).getRecipeID())) {
                holder.purrfectButton.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondary))
                listener.onPurrfect(recipes.get(position).getRecipeID(), recipes.get(position).recipeLikes)
            } else {
                holder.purrfectButton.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                listener.unPurrfect(recipes.get(position).getRecipeID(), recipes.get(position).recipeLikes)
            }
        }

        if (user!!.isPurrfectedRecipe(recipes.get(position).getRecipeID())) {
            holder.purrfectButton.setCardBackgroundColor(ContextCompat.getColor(context, R.color.secondary))
        } else if (!user!!.isPurrfectedRecipe(recipes.get(position).getRecipeID())) {
            holder.purrfectButton.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }

        holder.deleteRecipeButton.setOnClickListener {
            listener.onDelete(recipes.get(position))
        }

        holder.editRecipeButton.setOnClickListener {
            listener.onEditRecipe(recipes.get(position))
        }

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipes(list: ArrayList<Recipe>) {
        recipes=list
    }

    fun setUser(user: Customer)
    {
        this.user=user
    }
}