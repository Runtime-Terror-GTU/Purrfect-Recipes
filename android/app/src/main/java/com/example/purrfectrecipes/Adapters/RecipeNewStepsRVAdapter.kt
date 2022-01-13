package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Connectors.StepDeletedListener
import com.example.purrfectrecipes.R

class RecipeNewStepsRVAdapter(val context: Context, val listener: StepDeletedListener): RecyclerView.Adapter<RecipeNewStepsRVAdapter.ViewHolder>() {

    private var steps = ArrayList<String>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val stepNo=view.findViewById<TextView>(R.id.stepNo)
        val step=view.findViewById<EditText>(R.id.recipeStepDetails)
        val deleteStep=view.findViewById<ImageView>(R.id.deleteStep)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.added_recipe_steps_rv_bg, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.stepNo.text=(position+1).toString()
        holder.step.setText(steps.get(position))
        holder.deleteStep.setOnClickListener {
            listener.onStepDeleted(steps.get(position))
        }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    fun setSteps(list: ArrayList<String>) {
        steps = list
    }
}