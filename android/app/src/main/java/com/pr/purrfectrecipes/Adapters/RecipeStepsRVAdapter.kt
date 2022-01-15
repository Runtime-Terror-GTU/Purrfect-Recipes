package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.R

class RecipeStepsRVAdapter(val context: Context): RecyclerView.Adapter<RecipeStepsRVAdapter.ViewHolder>() {

    private var steps = ArrayList<String>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val stepNo=view.findViewById<TextView>(R.id.stepNo)
        val step=view.findViewById<TextView>(R.id.step)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recipe_steps_rv_bg, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stepNo.text=(position+1).toString()
        holder.step.text=steps.get(position)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    fun setSteps(list: ArrayList<String>) {
        steps = list
    }
}