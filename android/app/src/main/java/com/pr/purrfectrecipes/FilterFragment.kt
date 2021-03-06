package com.pr.purrfectrecipes

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pr.purrfectrecipes.Adapters.TagsRVAdapter
import com.pr.purrfectrecipes.Connectors.TagOnSelectedListener
import com.pr.purrfectrecipes.Customer.AddedrecipesProfileViewModel
import com.pr.purrfectrecipes.Customer.PurrfectedrecipesProfileViewModel
import com.pr.purrfectrecipes.Customer.RecipesHomeViewModel
import com.pr.purrfectrecipes.Customer.WhatresHomeViewModel
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Moderator.RecipesModeratorViewModel

class FilterFragment : Fragment(R.layout.fragment_filter), TagOnSelectedListener {

    private val homeViewModel: RecipesHomeViewModel by activityViewModels()
    private val whatResViewModel: WhatresHomeViewModel by activityViewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
    private val purrfectedRecipesViewModel:PurrfectedrecipesProfileViewModel by activityViewModels()
    private val moderatorRecipesViewModel:RecipesModeratorViewModel by activityViewModels()
    private val viewModel:FilterViewModel by activityViewModels()
    private var tagsRVAdapter: TagsRVAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRVAdapter()

        val loadingBar=view.findViewById<ProgressBar>(R.id.loadingBar)
        val mainPart=view.findViewById<LinearLayout>(R.id.mainPart)

        viewModel.getTags().observe(viewLifecycleOwner,{
            if(viewModel.getTags().value!=null) {
                tagsRVAdapter?.setTags(viewModel.getTags().value!!)
                tagsRVAdapter?.notifyDataSetChanged()
                loadingBar.visibility=View.GONE
                mainPart.visibility=View.VISIBLE
            }
        })

        val cancelButton=view.findViewById<ImageView>(R.id.cancelFilter)
        val enterButton=view.findViewById<LinearLayout>(R.id.enterFilterButton)

        val easyOption=view.findViewById<CheckBox>(R.id.easyOption)
        val mediumOption=view.findViewById<CheckBox>(R.id.mediumOption)
        val hardOption=view.findViewById<CheckBox>(R.id.hardOption)
        val diffArray=ArrayList<String>()
        diffArray.add("Easy")
        diffArray.add("Medium")
        diffArray.add("Hard")

        easyOption.setOnClickListener {
            if(easyOption.isChecked)
                viewModel.tempDifficulties.add(easyOption.text.toString())
            else
                viewModel.tempDifficulties.remove(easyOption.text.toString())
        }

        mediumOption.setOnClickListener {
            if(mediumOption.isChecked)
                viewModel.tempDifficulties.add(mediumOption.text.toString())
            else
                viewModel.tempDifficulties.remove(mediumOption.text.toString())
        }

        hardOption.setOnClickListener {
            if(hardOption.isChecked)
                viewModel.tempDifficulties.add(hardOption.text.toString())
            else
                viewModel.tempDifficulties.remove(hardOption.text.toString())
        }

        val direction= Hawk.get<String>(Constants.FILTER_DIRECTION)
        if(direction==Constants.MAIN_TO_FILTER)
        {
            cancelButton.setOnClickListener {
                homeViewModel.setFilter(false)
                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(viewModel.tempTags.size==0)
                    viewModel.setHomeTags(viewModel.getTags().value!!)
                else
                    viewModel.setHomeTags(viewModel.tempTags)

                if(viewModel.tempDifficulties.size==0)
                    viewModel.setHomeDifficulties(diffArray)
                else
                    viewModel.setHomeDifficulties(viewModel.tempDifficulties)

                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                homeViewModel.setFilter(false)
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            viewModel.getChosenTagsHome().observe(viewLifecycleOwner,{
                if(viewModel.getChosenTagsHome().value!=null && viewModel.getChosenTagsHome().value!!.size!=0 &&
                    viewModel.getChosenTagsHome().value!!.size!=viewModel.getTags().value!!.size)
                {
                    tagsRVAdapter?.setChosen(viewModel.getChosenTagsHome().value!!)
                    tagsRVAdapter?.notifyDataSetChanged()
                }
            })

            viewModel.getChosenDifficultiesHome().observe(viewLifecycleOwner, {
                if(viewModel.getChosenDifficultiesHome().value!=null && viewModel.getChosenDifficultiesHome().value!!.contains("Easy") &&
                    viewModel.getChosenDifficultiesHome().value!!.size!=3) {
                    easyOption.isChecked = true
                    viewModel.tempDifficulties.add("Easy")
                }
                if(viewModel.getChosenDifficultiesHome().value!=null && viewModel.getChosenDifficultiesHome().value!!.contains("Medium") &&
                    viewModel.getChosenDifficultiesHome().value!!.size!=3) {
                    mediumOption.isChecked = true
                    viewModel.tempDifficulties.add("Medium")
                }
                if(viewModel.getChosenDifficultiesHome().value!=null && viewModel.getChosenDifficultiesHome().value!!.contains("Hard") &&
                    viewModel.getChosenDifficultiesHome().value!!.size!=3) {
                    hardOption.isChecked = true
                    viewModel.tempDifficulties.add("Hard")
                }
            })
        }
        else if(direction==Constants.WHAT_TO_FILTER)
        {
            cancelButton.setOnClickListener {
                whatResViewModel.setFilter(false)
                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(viewModel.tempTags.size==0)
                    viewModel.setWhatTags(viewModel.getTags().value!!)
                else
                    viewModel.setWhatTags(viewModel.tempTags)

                if(viewModel.tempDifficulties.size==0)
                    viewModel.setWhatDifficulties(diffArray)
                else
                    viewModel.setWhatDifficulties(viewModel.tempDifficulties)

                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                whatResViewModel.setFilter(false)
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            viewModel.getChosenTagsWhat().observe(viewLifecycleOwner,{
                if(viewModel.getChosenTagsWhat().value!=null && viewModel.getChosenTagsWhat().value!!.size!=0 &&
                    viewModel.getChosenTagsWhat().value!!.size!=viewModel.getTags().value!!.size)
                {
                    tagsRVAdapter?.setChosen(viewModel.getChosenTagsWhat().value!!)
                    tagsRVAdapter?.notifyDataSetChanged()
                }
            })

            viewModel.getChosenDifficultiesWhat().observe(viewLifecycleOwner, {
                if(viewModel.getChosenDifficultiesWhat().value!=null && viewModel.getChosenDifficultiesWhat().value!!.contains("Easy") &&
                    viewModel.getChosenDifficultiesWhat().value!!.size!=3) {
                    easyOption.isChecked = true
                    viewModel.tempDifficulties.add("Easy")
                }
                if(viewModel.getChosenDifficultiesWhat().value!=null && viewModel.getChosenDifficultiesWhat().value!!.contains("Medium") &&
                    viewModel.getChosenDifficultiesWhat().value!!.size!=3) {
                    mediumOption.isChecked = true
                    viewModel.tempDifficulties.add("Medium")
                }
                if(viewModel.getChosenDifficultiesWhat().value!=null && viewModel.getChosenDifficultiesWhat().value!!.contains("Hard") &&
                    viewModel.getChosenDifficultiesWhat().value!!.size!=3) {
                    hardOption.isChecked = true
                    viewModel.tempDifficulties.add("Hard")
                }
            })
        }
        else if(direction==Constants.ADDED_TO_FILTER)
        {
            cancelButton.setOnClickListener {
                addedRecipesViewModel.setFilter(false)
                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(viewModel.tempTags.size==0)
                    viewModel.setAddedTags(viewModel.getTags().value!!)
                else
                    viewModel.setAddedTags(viewModel.tempTags)

                if(viewModel.tempDifficulties.size==0)
                    viewModel.setAddedDifficulties(diffArray)
                else
                    viewModel.setAddedDifficulties(viewModel.tempDifficulties)

                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                addedRecipesViewModel.setFilter(false)
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            viewModel.getChosenTagsAdded().observe(viewLifecycleOwner,{
                if(viewModel.getChosenTagsAdded().value!=null && viewModel.getChosenTagsAdded().value!!.size!=0 &&
                    viewModel.getChosenTagsAdded().value!!.size!=viewModel.getTags().value!!.size)
                {
                    tagsRVAdapter?.setChosen(viewModel.getChosenTagsAdded().value!!)
                    tagsRVAdapter?.notifyDataSetChanged()
                }
            })

            viewModel.getChosenDifficultiesAdded().observe(viewLifecycleOwner, {
                if(viewModel.getChosenDifficultiesAdded().value!=null && viewModel.getChosenDifficultiesAdded().value!!.contains("Easy") &&
                    viewModel.getChosenDifficultiesAdded().value!!.size!=3) {
                    easyOption.isChecked = true
                    viewModel.tempDifficulties.add("Easy")
                }
                if(viewModel.getChosenDifficultiesAdded().value!=null && viewModel.getChosenDifficultiesAdded().value!!.contains("Medium") &&
                    viewModel.getChosenDifficultiesAdded().value!!.size!=3) {
                    mediumOption.isChecked = true
                    viewModel.tempDifficulties.add("Medium")
                }
                if(viewModel.getChosenDifficultiesAdded().value!=null && viewModel.getChosenDifficultiesAdded().value!!.contains("Hard") &&
                    viewModel.getChosenDifficultiesAdded().value!!.size!=3) {
                    hardOption.isChecked = true
                    viewModel.tempDifficulties.add("Hard")
                }
            })
        }
        else if(direction==Constants.PURRFECTED_TO_FILTER)
        {
            cancelButton.setOnClickListener {
                purrfectedRecipesViewModel.setFilter(false)
                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(viewModel.tempTags.size==0)
                    viewModel.setPurrfectedTags(viewModel.getTags().value!!)
                else
                    viewModel.setPurrfectedTags(viewModel.tempTags)

                if(viewModel.tempDifficulties.size==0)
                    viewModel.setPurrfectedDifficulties(diffArray)
                else
                    viewModel.setPurrfectedDifficulties(viewModel.tempDifficulties)

                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                purrfectedRecipesViewModel.setFilter(false)
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            viewModel.getChosenTagsPurrfected().observe(viewLifecycleOwner,{
                if(viewModel.getChosenTagsPurrfected().value!=null && viewModel.getChosenTagsPurrfected().value!!.size!=0 &&
                    viewModel.getChosenTagsPurrfected().value!!.size!=viewModel.getTags().value!!.size)
                {
                    tagsRVAdapter?.setChosen(viewModel.getChosenTagsPurrfected().value!!)
                    tagsRVAdapter?.notifyDataSetChanged()
                }
            })

            viewModel.getChosenDifficultiesPurrfected().observe(viewLifecycleOwner, {
                if(viewModel.getChosenDifficultiesPurrfected().value!=null && viewModel.getChosenDifficultiesPurrfected().value!!.contains("Easy") &&
                    viewModel.getChosenDifficultiesPurrfected().value!!.size!=3) {
                    easyOption.isChecked = true
                    viewModel.tempDifficulties.add("Easy")
                }
                if(viewModel.getChosenDifficultiesPurrfected().value!=null && viewModel.getChosenDifficultiesPurrfected().value!!.contains("Medium") &&
                    viewModel.getChosenDifficultiesPurrfected().value!!.size!=3) {
                    mediumOption.isChecked = true
                    viewModel.tempDifficulties.add("Medium")
                }
                if(viewModel.getChosenDifficultiesPurrfected().value!=null && viewModel.getChosenDifficultiesPurrfected().value!!.contains("Hard") &&
                    viewModel.getChosenDifficultiesPurrfected().value!!.size!=3) {
                    hardOption.isChecked = true
                    viewModel.tempDifficulties.add("Hard")
                }
            })
        }
        else if(direction==Constants.MODERATOR_TO_FILTER)
        {
            cancelButton.setOnClickListener {
                moderatorRecipesViewModel.setFilter(false)
                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(viewModel.tempTags.size==0)
                    viewModel.setModeratorTags(viewModel.getTags().value!!)
                else
                    viewModel.setModeratorTags(viewModel.tempTags)

                if(viewModel.tempDifficulties.size==0)
                    viewModel.setModeratorDifficulties(diffArray)
                else
                    viewModel.setModeratorDifficulties(viewModel.tempDifficulties)

                viewModel.tempTags.clear()
                viewModel.tempDifficulties.clear()
                moderatorRecipesViewModel.setFilter(false)
                Hawk.delete(Constants.FILTER_DIRECTION)
            }

            viewModel.getChosenTagsModerator().observe(viewLifecycleOwner,{
                if(viewModel.getChosenTagsModerator().value!=null && viewModel.getChosenTagsModerator().value!!.size!=0 &&
                    viewModel.getChosenTagsModerator().value!!.size!=viewModel.getTags().value!!.size)
                {
                    tagsRVAdapter?.setChosen(viewModel.getChosenTagsModerator().value!!)
                    tagsRVAdapter?.notifyDataSetChanged()
                }
            })

            viewModel.getChosenDifficultiesModerator().observe(viewLifecycleOwner, {
                if(viewModel.getChosenDifficultiesModerator().value!=null && viewModel.getChosenDifficultiesModerator().value!!.contains("Easy") &&
                    viewModel.getChosenDifficultiesModerator().value!!.size!=3) {
                    easyOption.isChecked = true
                    viewModel.tempDifficulties.add("Easy")
                }
                if(viewModel.getChosenDifficultiesModerator().value!=null && viewModel.getChosenDifficultiesModerator().value!!.contains("Medium") &&
                    viewModel.getChosenDifficultiesModerator().value!!.size!=3) {
                    mediumOption.isChecked = true
                    viewModel.tempDifficulties.add("Medium")
                }
                if(viewModel.getChosenDifficultiesModerator().value!=null && viewModel.getChosenDifficultiesModerator().value!!.contains("Hard") &&
                    viewModel.getChosenDifficultiesModerator().value!!.size!=3) {
                    hardOption.isChecked = true
                    viewModel.tempDifficulties.add("Hard")
                }
            })
        }
    }

    fun setRVAdapter()
    {
        val recipeTypes = view?.findViewById<RecyclerView>(R.id.recipeTypes)
        recipeTypes?.layoutManager = GridLayoutManager(requireActivity(), 3)
        tagsRVAdapter = TagsRVAdapter(requireContext(), this)
        recipeTypes?.adapter = tagsRVAdapter
    }

    override fun onSelectTag(selectedTag: String) {
        viewModel.tempTags.add(selectedTag)
    }

    override fun deselectTag(deSelectedTag: String) {
        viewModel.tempTags.remove(deSelectedTag)
    }
}