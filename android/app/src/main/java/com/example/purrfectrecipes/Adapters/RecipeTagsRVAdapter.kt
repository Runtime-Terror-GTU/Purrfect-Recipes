package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.R

class RecipeTagsRVAdapter(val context: Context): RecyclerView.Adapter<RecipeTagsRVAdapter.ViewHolder>()
{
    private var tags=ArrayList<String>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val tag=view.findViewById<CheckBox>(R.id.recipeTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_tags_rv_bg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tag.text=tags.get(position)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setTags(list:ArrayList<String>)
    {
        tags=list
    }

}