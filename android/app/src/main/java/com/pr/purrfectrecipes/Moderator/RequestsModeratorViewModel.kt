package com.pr.purrfectrecipes.Moderator

import android.app.Activity
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.RequestsModeratorVMRepConnecter

class RequestsModeratorViewModel: ViewModel(), RequestsModeratorVMRepConnecter
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private val repository = RequestsModeratorRepository(this)
    private var suggestions= MutableLiveData<ArrayList<String>?>()
    fun getSuggestions(): LiveData<ArrayList<String>?> {return suggestions}
    private var size = MutableLiveData<Int>(0)
    fun getSize(): LiveData<Int> {return size}

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.retrieveSuggestions()
    }
    override fun onSuggestionsRetrieved(list:ArrayList<String>?){
        if(list!=null){
            suggestions.value=list
        }
    }
    override fun size(count:Int){
        size.value=count

    }
    override fun suggestionApprove(suggestedIngredient:String){
        repository.approveSuggestion(suggestedIngredient)
    }
    override fun suggestionDeny(suggestedIngredient:String){
        repository.denySuggestion(suggestedIngredient)
    }

}