package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.example.purrfectrecipes.Connectors.TagOnSelectedListener
import com.example.purrfectrecipes.R

class IngredientsRVAdapter(val context: Context, val listener:IngredientOnSelectedListener): RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder>()
{
    private var ingredients=ArrayList<String>()
    private var chosenIngredients=ArrayList<String>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val ingredientName=view.findViewById<TextView>(R.id.ingredientName)
        val chooseOption=view.findViewById<CheckBox>(R.id.chooseOption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.ingredients_rv_bg, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredientName.text=ingredients.get(position)
        holder.chooseOption.setOnClickListener {
            if(holder.chooseOption.isChecked)
            {
                holder.chooseOption.text="Chosen"
                listener.onSelectIngredient(holder.ingredientName.text.toString())
            }
            else{
                holder.chooseOption.text="Choose"
                listener.deselectIngredient(holder.ingredientName.text.toString())
            }
        }

        if(chosenIngredients.contains(holder.ingredientName.text.toString()))
        {
            holder.chooseOption.isChecked=true
            listener.onSelectIngredient(holder.ingredientName.text.toString())
        }

    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    fun setIngredients(list:ArrayList<String>)
    {
        ingredients=list
    }

    fun setChosen(chosenList:ArrayList<String>)
    {
        chosenIngredients=chosenList
    }
}