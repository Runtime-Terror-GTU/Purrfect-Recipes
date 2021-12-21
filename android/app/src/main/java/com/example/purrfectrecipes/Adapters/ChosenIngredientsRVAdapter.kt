package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.example.purrfectrecipes.R

class ChosenIngredientsRVAdapter(val context: Context): RecyclerView.Adapter<ChosenIngredientsRVAdapter.ViewHolder>()
{
    private var ingredients=ArrayList<String>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val ingredientName=view.findViewById<CheckBox>(R.id.ingredientName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.chosen_ingredients_rv_bg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientName.text=ingredients.get(position)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setIngredients(list:ArrayList<String>)
    {
        ingredients=list
    }

}