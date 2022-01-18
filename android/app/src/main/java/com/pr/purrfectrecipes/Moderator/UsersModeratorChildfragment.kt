package com.pr.purrfectrecipes.Moderator

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.UsersRVAdapter
import com.example.purrfectrecipes.Connectors.UsersDeleteOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.Customer

class UsersModeratorChildfragment: Fragment(R.layout.childfragment_moderator_users),
    UsersDeleteOnClickListener
{
    private val viewModel: UsersModeratorViewModel by activityViewModels()
    private var usersRVAdapter: UsersRVAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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

            }
        })
        setRVAdapter()
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                usersRVAdapter?.filter?.filter(newText)
                return false
            }

        })
        usersRVAdapter?.notifyDataSetChanged()
        setRVAdapter()
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