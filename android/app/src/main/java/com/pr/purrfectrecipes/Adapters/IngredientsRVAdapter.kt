package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Connectors.IngredientOnSelectedListener
import com.pr.purrfectrecipes.R

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
                if(!chosenIngredients.contains(holder.ingredientName.text.toString())) {
                    listener.onSelectIngredient(holder.ingredientName.text.toString())
                    chosenIngredients.add(holder.ingredientName.text.toString())
                }
            }
            else{
                holder.chooseOption.text="Choose"
                chosenIngredients.remove(holder.ingredientName.text.toString())
                listener.deselectIngredient(holder.ingredientName.text.toString())
            }
        }

        if(chosenIngredients.contains(holder.ingredientName.text.toString()))
        {
            holder.chooseOption.isChecked=true
            holder.chooseOption.text="Chosen"
        }
        else if(!chosenIngredients.contains(holder.ingredientName.text.toString()))
        {
            holder.chooseOption.isChecked=false
            holder.chooseOption.text="Choose"
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
        chosenIngredients.addAll(chosenList)
    }
}