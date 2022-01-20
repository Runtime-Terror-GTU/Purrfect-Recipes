package com.pr.purrfectrecipes.Admin

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Adapters.RemoveModRVAdapter
import com.pr.purrfectrecipes.Connectors.ModDeleteOnClickListener
import com.pr.purrfectrecipes.R
import com.pr.purrfectrecipes.User.Customer
import java.util.*
import kotlin.collections.ArrayList


class RemoveAdminChildfragment: Fragment(R.layout.childfragment_admin_remove),
    ModDeleteOnClickListener
{
    private val viewModel: RemoveAdminViewModel by activityViewModels()
    private var modsRVAdapter: RemoveModRVAdapter?=null
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
        var searchView = view.findViewById<SearchView>(R.id.searchMods)
        viewModel.getMods().observe(viewLifecycleOwner, {
            if(viewModel.getMods().value!=null){
                modsRVAdapter?.setModsList(viewModel.getMods().value!!)
                modsRVAdapter?.notifyDataSetChanged()

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
                    for(user in viewModel.getMods().value!!)
                        if(user.getUsername().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)))
                            tempArray.add(user)
                    modsRVAdapter?.setModsList(tempArray)
                    modsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    modsRVAdapter?.setModsList(viewModel.getMods().value!!)
                    modsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false

            }
            override fun onQueryTextChange(query: String): Boolean {
                if(!query.isNullOrEmpty())
                {
                    val tempArray=ArrayList<Customer>()
                    for(user in viewModel.getMods().value!!)
                        if(user.getUsername().lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT)))
                            tempArray.add(user)
                    modsRVAdapter?.setModsList(tempArray)
                    modsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else if(query.isNullOrEmpty())
                {
                    modsRVAdapter?.setModsList(viewModel.getMods().value!!)
                    modsRVAdapter?.notifyDataSetChanged()
                    return true
                }
                else
                    return false
            }
        })

    }
    override fun onDeleteClick(modID:String){
        viewModel.deleteMod(modID)
    }
    fun setRVAdapter()
    {
        val mods = view?.findViewById<RecyclerView>(R.id.mods)
        mods?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        modsRVAdapter = RemoveModRVAdapter(requireContext(),this)
        mods?.adapter = modsRVAdapter
    }

}