package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Connectors.UsersDeleteOnClickListener
import com.example.purrfectrecipes.User.UserClass
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.CustomerStatus
import java.util.*
import kotlin.collections.ArrayList

class UsersRVAdapter(val context: Context , val listener: UsersDeleteOnClickListener): RecyclerView.Adapter<UsersRVAdapter.ViewHolder>()
    , Filterable {
    private var users=ArrayList<UserClass>()
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val userName =  view.findViewById<TextView>(R.id.userName)
        val premiumUserSymbol = view.findViewById<ImageView>(R.id.premiumUserSymbol)
        val profilePic = view.findViewById<ImageView>(R.id.profilePic)
        val recipes = view.findViewById<TextView>(R.id.recipes)
        val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRVAdapter.ViewHolder {

        return UsersRVAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.users_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text =  users[position].getUsername()
        if(!users[position].getUserStatus().equals(CustomerStatus.PREMIUM.text)){
            holder.premiumUserSymbol.visibility=View.GONE
        }
        holder.recipes.text= users[position].getAddedRecipeNum().toString() + " recipes"
        /*
        holder.recipePic.setOnClickListener {
            listener.onRecipeClick(recipes.get(position).getRecipeID())
        }
         */
        holder.deleteButton.setOnClickListener{
            listener.onDeleteClick(users[position].getUserID())
        }

    }
    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsersList(list:ArrayList<UserClass>){
        users=list
    }
    fun getUser(position: Int):UserClass{
        return users.get(position)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val resultList = ArrayList<UserClass>()
                if (!charSearch.isEmpty()) {
                    for (row in users) {
                        if (row.getUsername().lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    users = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = users
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                users = results?.values as ArrayList<UserClass>
                notifyDataSetChanged()
            }

        }
    }


}