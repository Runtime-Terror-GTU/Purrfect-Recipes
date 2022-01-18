package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Connectors.ModDeleteOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.Customer
import java.util.*
import kotlin.collections.ArrayList

class RemoveModRVAdapter(val context: Context, val listener: ModDeleteOnClickListener): RecyclerView.Adapter<RemoveModRVAdapter.ViewHolder>()
    , Filterable {
    private var mods= ArrayList<Customer>()
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
        {
            val userName =  view.findViewById<TextView>(R.id.userName)
            val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.users_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text =  mods[position].getUsername()

        holder.deleteButton.setOnClickListener{
            deleteMod(position)
            listener.onDeleteClick(mods[position].getUserID())
        }
    }
    override fun getItemCount(): Int {
        return mods.size
    }

    fun setModsList(list:ArrayList<Customer>){
        mods=list
    }
    private fun deleteMod(position: Int){
        mods.removeAt(position)
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val resultList = ArrayList<Customer>()
                if (!charSearch.isEmpty()) {
                    for (row in mods) {
                        if (row.getUsername().lowercase(Locale.ROOT).contains(charSearch.lowercase(
                                Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    mods = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = mods
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mods = results?.values as ArrayList<Customer>
                notifyDataSetChanged()
            }

        }

    }
}