package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr.purrfectrecipes.Connectors.ModDeleteRecipeOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.Recipe

class ModeratorRecipeRVAdapter(val context: Context, val listener: ModDeleteRecipeOnClickListener): RecyclerView.Adapter<ModeratorRecipeRVAdapter.ViewHolder>()
{

    private var recipes=ArrayList<Recipe>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val deleteRecipeButton =  view.findViewById<ImageView>(R.id.deleteRecipeButton)
        val recipePic = view.findViewById<ImageView>(R.id.recipePic)
        val recipeOwner = view.findViewById<TextView>(R.id.recipeOwner)
        val recipeName = view.findViewById<TextView>(R.id.recipeName)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recipes_rv_mod, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipeName.text =  recipes[position].recipeName
        if(recipes.get(position).recipePictureURL!=" ")
        {
            Glide.with(context)
                .load(recipes.get(position).recipePictureURL)
                .into(holder.recipePic)
        }

        holder.deleteRecipeButton.setOnClickListener{
            listener.onDeleteClick(recipes[position])
            deleteRecipe(position)
        }


    }
    override fun getItemCount(): Int {
        return recipes.size
    }
    fun deleteRecipe(position: Int){
        recipes.removeAt(position)
    }

    fun setRecipeList(list:ArrayList<Recipe>){
        recipes=list
    }
    fun getRecipe(position: Int): Recipe {
        return recipes.get(position)
    }
}