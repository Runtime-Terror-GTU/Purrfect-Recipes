package com.pr.purrfectrecipes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pr.purrfectrecipes.Connectors.ModDeleteOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.Customer

class RemoveModRVAdapter(val context: Context, val listener: ModDeleteOnClickListener): RecyclerView.Adapter<RemoveModRVAdapter.ViewHolder>()
     {
    private var mods= ArrayList<Customer>()
        class ViewHolder(view: View): RecyclerView.ViewHolder(view)
        {
            val userName =  view.findViewById<TextView>(R.id.userName)
            val deleteButton = view.findViewById<ImageView>(R.id.deleteButton)
            val premiumUserSymbol = view.findViewById<ImageView>(R.id.premiumUserSymbol)
            val profilePic = view.findViewById<ImageView>(R.id.profilePic)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.users_rv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text =  mods[position].getUsername()
        holder.premiumUserSymbol.visibility=View.GONE

        holder.deleteButton.setOnClickListener{
            deleteMod(position)
            listener.onDeleteClick(mods[position].getUserID())
        }

        Glide.with(context)
            .load(mods.get(position).getUserPic())
            .into(holder.profilePic)
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

}