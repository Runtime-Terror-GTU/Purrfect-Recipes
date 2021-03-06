package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.UsersRVAdapter
import com.example.purrfectrecipes.Connectors.UsersDeleteOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.Customer
import java.util.*
import kotlin.collections.ArrayList

class UsersModeratorChildfragment: Fragment(R.layout.childfragment_moderator_users),
    UsersDeleteOnClickListener
{
    private val viewModel: UsersModeratorViewModel by activityViewModels()
    private var usersRVAdapter: UsersRVAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainLayout=view.findViewById<LinearLayout>(R.id.mainLayout)
        val progressBar=view.findViewById<ProgressBar>(R.id.loadingBar)

        viewModel.getView().observe(viewLifecycleOwner, {
            if(viewModel.getView().value!=null)
                super.onViewCreated(viewModel.getView().value!!, savedInstanceState)
            else
            {
                viewModel.setView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })
        var searchView = view.findViewById<SearchView>(R.id.searchUser)

        viewModel.getUsers().observe(viewLifecycleOwner, {
            if(viewModel.getUsers().value!=null){
                usersRVAdapter?.setUsersList(viewModel.getUsers().value!!)
                usersRVAdapter?.notifyDataSetChanged()

                mainLayout.visibility=View.VISIBLE
                progressBar.visibility=View.GONE

            }
        })
        setRVAdapter()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<Customer>()
                    for(user in viewModel.getUsers().value!!)
                        if(user.getUsername().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)))
                            tempArray.add(user)
                    usersRVAdapter?.setUsersList(tempArray)
                    usersRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    usersRVAdapter?.setUsersList(viewModel.getUsers().value!!)
                    usersRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false

            }
            override fun onQueryTextChange(query: String): Boolean {
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<Customer>()
                    for(user in viewModel.getUsers().value!!)
                        if(user.getUsername().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)))
                            tempArray.add(user)
                    usersRVAdapter?.setUsersList(tempArray)
                    usersRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    usersRVAdapter?.setUsersList(viewModel.getUsers().value!!)
                    usersRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false
            }
        })

    }
    fun setRVAdapter()
    {
        val users = view?.findViewById<RecyclerView>(R.id.users)
        users?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        usersRVAdapter = UsersRVAdapter(requireContext(),this)
        users?.adapter = usersRVAdapter
    }

    override fun onDeleteClick(user: Customer) {
        viewModel.deleteUser(user,requireActivity())
    }
}