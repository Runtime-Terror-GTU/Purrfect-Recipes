package com.example.purrfectrecipes.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purrfectrecipes.Comment
import com.example.purrfectrecipes.Connectors.CommentChangeListener
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.R
import com.example.purrfectrecipes.User.CustomerStatus
import com.orhanobut.hawk.Hawk

class CommentsRVAdapter (val context: Context, val listener:CommentChangeListener): RecyclerView.Adapter<CommentsRVAdapter.ViewHolder>() {
    private var comments = ArrayList<Comment>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentOwnerPic=view.findViewById<ImageView>(R.id.commentOwnerPic)
        val commentOwnerName=view.findViewById<TextView>(R.id.ownerName)
        val commentContent=view.findViewById<TextView>(R.id.comment)
        val deleteButton=view.findViewById<ImageView>(R.id.deleteCommentButton)
        val premiumSymbol=view.findViewById<ImageView>(R.id.premiumSymbol)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.comments_rv_bg, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.commentOwnerName.text=comments.get(position).ownerName
        holder.commentContent.text=comments.get(position).getCommentContent()

        if(comments.get(position).ownerPicURL!=" " && comments.get(position).ownerPicURL!=null) {
            Glide.with(context)
                .load(comments.get(position).ownerPicURL)
                .into(holder.commentOwnerPic)
        }

        if(comments.get(position).ownerStatus==CustomerStatus.PREMIUM)
            holder.premiumSymbol.visibility=View.VISIBLE
        else
            holder.premiumSymbol.visibility=View.GONE

        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)==comments.get(position).getOwnerId() ||
            Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)==CustomerStatus.MODERATOR)
            holder.deleteButton.visibility=View.VISIBLE
        else
            holder.deleteButton.visibility=View.GONE

        holder.deleteButton.setOnClickListener {
            listener.onDeleteComment(comments.get(position).getId())
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun setComments(list: ArrayList<Comment>) {
        comments = list
    }
}
