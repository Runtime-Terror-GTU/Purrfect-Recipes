package com.example.purrfectrecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.User.Customer
import com.orhanobut.hawk.Hawk

class RecipeViewModel : ViewModel(), RecipeRetrievedListener
{
    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}
    var recipeOwner:Customer?=null
    var user:Customer?=null
    private var comments= MutableLiveData<ArrayList<Comment>>()
        fun getComments(): LiveData<ArrayList<Comment>> {return comments}

    private val repository=RecipeRepository(this)

    init{
        repository.retrieveUser()
    }

    fun setRecipe(id: String)
    {
        repository.retrieveRecipe(id)
    }

    fun resetRecipe()
    {
        recipe.value=null
    }

    fun addComment(commentText:String)
    {
        val newCommentId=System.currentTimeMillis().toString()
        val newComment=Comment(newCommentId, Hawk.get<String>(Constants.LOGGEDIN_USERID), commentText)
        newComment.ownerName=user?.getUsername()
        newComment.ownerPicURL=user?.getUserPic()
        newComment.ownerStatus=user?.getUserStatus()
        comments.value?.add(newComment)
        recipe.value?.addComment(newCommentId)
        repository.saveComment(newComment, recipe.value!!.getRecipeID())
        comments.value=comments.value
    }

    fun deleteComment(commentId:String)
    {
        var deletedComment:Comment?=null
        for(comment in comments.value!!)
            if(comment.getId()==commentId)
                deletedComment=comment
        deletedComment?.ownerName=user?.getUsername()
        deletedComment?.ownerPicURL=user?.getUserPic()
        deletedComment?.ownerStatus=user?.getUserStatus()
        comments.value!!.remove(deletedComment)
        recipe.value?.removeComment(deletedComment!!.getId())
        repository.removeComment(deletedComment, recipe.value!!.getRecipeID())
        comments.value=comments.value
    }

    fun deleteRecipe()
    {
        repository.removeRecipe(recipe.value!!)
    }

    fun purrfectRecipe()
    {
        val currentLikes=recipe.value!!.recipeLikes

        recipe.value!!.recipeLikes=currentLikes+1
        user?.addPurrfectedRecipe(recipe.value!!.getRecipeID())
        recipe.value=recipe.value

        repository.increasePurrfectedCount(recipe.value!!.getRecipeID(), currentLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    fun unPurrfectRecipe()
    {
        val currentLikes=recipe.value!!.recipeLikes

        recipe.value!!.recipeLikes=currentLikes-1
        user?.removePurrfectedRecipe(recipe.value!!.getRecipeID())
        recipe.value=recipe.value

        repository.decreasePurrfectedCount(recipe.value!!.getRecipeID(), currentLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    override fun onUserRetrieved(user:Customer?)
    {
        this.user=user
    }

    override fun onRecipeRetrieved(recipe: Recipe, recipeOwner:Customer, comments:ArrayList<Comment>) {
        this.recipeOwner=recipeOwner
        this.recipe.value=recipe
        this.comments.value=comments
    }
}