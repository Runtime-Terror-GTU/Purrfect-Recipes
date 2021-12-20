package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.Recipe

class TagsRVAdapter(val context: Context): RecyclerView.Adapter<TagsRVAdapter.ViewHolder>()
{
    private var tags=ArrayList<String>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val tagName=view.findViewById<CheckBox>(R.id.recipeTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_types_rv_bg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName.text=tags.get(position)

    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setTags(list:ArrayList<String>)
    {
        tags=list
    }
}