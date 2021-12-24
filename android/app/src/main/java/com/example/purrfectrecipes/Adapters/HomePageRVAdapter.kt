package com.example.purrfectrecipes.Adapters

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
import com.example.purrfectrecipes.Connectors.RecipeOnClickListener
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

class HomePageRVAdapter(val context: Context, val listener:RecipeOnClickListener): RecyclerView.Adapter<HomePageRVAdapter.ViewHolder>()
{
    private var recipes=ArrayList<Recipe>()
    private var user:Customer?=null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val recipePic=view.findViewById<ImageView>(R.id.recipePicRV)
        val recipeName=view.findViewById<TextView>(R.id.recipeNameRV)
        val recipeOwner=view.findViewById<TextView>(R.id.recipeOwnerRV)
        val recipeLikes=view.findViewById<TextView>(R.id.recipeLikesRV)
        val recipeDifficulty=view.findViewById<TextView>(R.id.recipeDifficultyRV)
        val recipeType=view.findViewById<TextView>(R.id.recipeTypeRV)
        val purrfectButton=view.findViewById<CardView>(R.id.purrfectButtonRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_recipe_rvbg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recipeName.text=recipes.get(position).recipeName
        holder.recipeOwner.text="by "+recipes.get(position).recipeOwner
        holder.recipeLikes.text=recipes.get(position).recipeLikes.toString()
        holder.recipeDifficulty.text=recipes.get(position).recipeDifficulty
        if(recipes.get(position).getRecipeTags().size!=0)
            holder.recipeType.text=recipes.get(position).getRecipeTags().get(0)
        else
            holder.recipeType.text="Meal"
        if(recipes.get(position).recipePictureURL!=" ")
        {
            Glide.with(context)
                .load(recipes.get(position).recipePictureURL)
                .into(holder.recipePic)
        }

        holder.recipePic.setOnClickListener {
            listener.onRecipeClick(recipes.get(position).getRecipeID())
        }

        if(user!=null) {
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
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipes(list:ArrayList<Recipe>)
    {
        recipes=list
    }

    fun setUser(user: Customer)
    {
        this.user=user
    }


}