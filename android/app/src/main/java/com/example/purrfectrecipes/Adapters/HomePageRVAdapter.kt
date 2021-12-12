package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.Recipe

class HomePageRVAdapter(val context: Context): RecyclerView.Adapter<HomePageRVAdapter.ViewHolder>()
{
    private var recipes=ArrayList<Recipe>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val recipePic=view.findViewById<ImageView>(R.id.recipePicRV)
        val recipeName=view.findViewById<TextView>(R.id.recipeNameRV)
        val recipeOwner=view.findViewById<TextView>(R.id.recipeOwnerRV)
        val recipeLikes=view.findViewById<TextView>(R.id.recipeLikesRV)
        val recipeDifficulty=view.findViewById<TextView>(R.id.recipeDifficultyRV)
        val recipeType=view.findViewById<TextView>(R.id.recipeTypeRV)
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
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipes(list:ArrayList<Recipe>)
    {
        recipes=list
    }


}