package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Connectors.RequestOnClickListener
import com.pr.purrfectrecipes.R

class IngredientRequestsPageRVAdapter(val context: Context, val listener: RequestOnClickListener): RecyclerView.Adapter<IngredientRequestsPageRVAdapter.ViewHolder>()
{

    private var suggestions=ArrayList<String>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {

        val suggestion=view.findViewById<TextView>(R.id.suggestion)
        val approveButton=view.findViewById<ImageView>(R.id.approveButton)
        val denyButton=view.findViewById<ImageView>(R.id.denyButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.suggestions_rv, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.suggestion.text = suggestions.get(position)
        val suggestionStr = holder.suggestion.text.removeRange(0,22)
        holder.approveButton.setOnClickListener {
            listener.onApproveClick(suggestionStr.toString())
        }
        holder.denyButton.setOnClickListener {
            listener.onDenyClick(suggestionStr.toString())
        }
    }

    override fun getItemCount(): Int {
        return suggestions.size
    }

    fun setSuggestions(list:ArrayList<String>){
        suggestions=list
    }
    fun getSuggestion(position: Int):String{
        return suggestions.get(position)
    }



}