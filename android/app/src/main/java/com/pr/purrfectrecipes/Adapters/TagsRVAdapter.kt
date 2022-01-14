package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Connectors.TagOnSelectedListener
import com.pr.purrfectrecipes.R

class TagsRVAdapter(val context: Context, val connector: TagOnSelectedListener): RecyclerView.Adapter<TagsRVAdapter.ViewHolder>()
{
    private var tags=ArrayList<String>()
    private var chosenTags=ArrayList<String>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val tagName=view.findViewById<CheckBox>(R.id.recipeTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_types_rv_bg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagName.text=tags.get(position)
        holder.tagName.setOnClickListener {
            if(holder.tagName.isChecked)
                connector.onSelectTag(holder.tagName.text.toString())
            else
                connector.deselectTag(holder.tagName.text.toString())
        }
        if(chosenTags.contains(holder.tagName.text)) {
            holder.tagName.isChecked = true
            connector.onSelectTag(holder.tagName.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setTags(list:ArrayList<String>)
    {
        tags=list
    }

    fun setChosen(chosenList:ArrayList<String>)
    {
        chosenTags=chosenList
    }
}