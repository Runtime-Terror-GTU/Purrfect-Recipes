package com.example.purrfectrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.purrfectrecipes.Adapters.HomePageRVAdapter
import com.example.purrfectrecipes.Adapters.TagsRVAdapter
import com.example.purrfectrecipes.Connectors.TagOnSelectedListener
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel
import com.orhanobut.hawk.Hawk

class FilterFragment : Fragment(R.layout.fragment_filter), TagOnSelectedListener {

    private val homeViewModel: RecipesHomeViewModel by activityViewModels()
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