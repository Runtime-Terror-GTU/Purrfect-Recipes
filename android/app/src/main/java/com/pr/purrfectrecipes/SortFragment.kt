package com.pr.purrfectrecipes

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.pr.purrfectrecipes.Customer.*
import com.orhanobut.hawk.Hawk

class SortFragment: Fragment(R.layout.fragment_sort) {

    private val homeViewModel:RecipesHomeViewModel by activityViewModels()
    private val whatResViewModel: WhatresHomeViewModel by activityViewModels()
    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()
    private val purrfectedRecipesViewModel:PurrfectedrecipesProfileViewModel by activityViewModels()

    private val viewModel:SortViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val sortMethod=view.findViewById<RadioGroup>(R.id.sortMethod)

        val cancelButton=view.findViewById<ImageView>(R.id.cancelSort)
        val enterButton=view.findViewById<LinearLayout>(R.id.enterSortButton)

        val diffHardest=view.findViewById<RadioButton>(R.id.diffHardest)
        val diffEasiest=view.findViewById<RadioButton>(R.id.diffEasiest)
        val popMax=view.findViewById<RadioButton>(R.id.popMost)
        val popMin=view.findViewById<RadioButton>(R.id.popLeast)

        val direction= Hawk.get<String>(Constants.SORT_DIRECTION)
        //Toast.makeText(requireContext(), direction.toString(), Toast.LENGTH_SHORT).show()
        if(direction==Constants.MAIN_TO_SORT)
        {
            viewModel.getHomeSortId().observe(viewLifecycleOwner,{
                if(viewModel.getHomeSortId().value!=-1 && sortMethod.checkedRadioButtonId==-1) {
                    if(viewModel.getHomeSortId().value==0)
                        diffHardest.isChecked=true
                    else if(viewModel.getHomeSortId().value==1)
                        diffEasiest.isChecked=true
                    else if(viewModel.getHomeSortId().value==2)
                        popMin.isChecked=true
                    else if(viewModel.getHomeSortId().value==3)
                        popMax.isChecked=true
                }
            })

            diffHardest.setOnClickListener {
                viewModel.setPopHomeSort(null)
                viewModel.setDiffHomeSort(SortMethods.difMaxtoMin)
                viewModel.setHomeSortId(0)
            }
            diffEasiest.setOnClickListener {
                viewModel.setPopHomeSort(null)
                viewModel.setDiffHomeSort(SortMethods.difMintoMax)
                viewModel.setHomeSortId(1)
            }
            popMax.setOnClickListener {
                viewModel.setDiffHomeSort(null)
                viewModel.setPopHomeSort(SortMethods.popMaxtoMin)
                viewModel.setHomeSortId(3)
            }
            popMin.setOnClickListener {
                viewModel.setDiffHomeSort(null)
                viewModel.setPopHomeSort(SortMethods.popMintoMax)
                viewModel.setHomeSortId(2)
            }

            cancelButton.setOnClickListener {
                viewModel.resetHomeSort()
                homeViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(sortMethod.checkedRadioButtonId==-1) {
                    Toast.makeText(requireContext(), "Choose a sort method first.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    homeViewModel.setSort(false)
                    Hawk.delete(Constants.SORT_DIRECTION)
                }
            }
        }
        else if(direction==Constants.WHAT_TO_SORT)
        {
            viewModel.getWhatSortId().observe(viewLifecycleOwner,{
                if(viewModel.getWhatSortId().value!=-1 && sortMethod.checkedRadioButtonId==-1) {
                    if(viewModel.getWhatSortId().value==0)
                        diffHardest.isChecked=true
                    else if(viewModel.getWhatSortId().value==1)
                        diffEasiest.isChecked=true
                    else if(viewModel.getWhatSortId().value==2)
                        popMin.isChecked=true
                    else if(viewModel.getWhatSortId().value==3)
                        popMax.isChecked=true
                }
            })

            diffHardest.setOnClickListener {
                viewModel.setPopWhatSort(null)
                viewModel.setDiffWhatSort(SortMethods.difMaxtoMin)
                viewModel.setWhatSortId(0)
            }
            diffEasiest.setOnClickListener {
                viewModel.setPopWhatSort(null)
                viewModel.setDiffWhatSort(SortMethods.difMintoMax)
                viewModel.setWhatSortId(1)
            }
            popMax.setOnClickListener {
                viewModel.setDiffWhatSort(null)
                viewModel.setPopWhatSort(SortMethods.popMaxtoMin)
                viewModel.setWhatSortId(3)
            }
            popMin.setOnClickListener {
                viewModel.setDiffWhatSort(null)
                viewModel.setPopWhatSort(SortMethods.popMintoMax)
                viewModel.setWhatSortId(2)
            }

            cancelButton.setOnClickListener {
                viewModel.resetWhatSort()
                whatResViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(sortMethod.checkedRadioButtonId==-1) {
                    Toast.makeText(requireContext(), "Choose a sort method first.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    whatResViewModel.setSort(false)
                    Hawk.delete(Constants.SORT_DIRECTION)
                }
            }
        }
        else if(direction==Constants.ADDED_TO_SORT)
        {
            viewModel.getAddedSortId().observe(viewLifecycleOwner,{
                if(viewModel.getAddedSortId().value!=-1 && sortMethod.checkedRadioButtonId==-1) {
                    if(viewModel.getAddedSortId().value==0)
                        diffHardest.isChecked=true
                    else if(viewModel.getAddedSortId().value==1)
                        diffEasiest.isChecked=true
                    else if(viewModel.getAddedSortId().value==2)
                        popMin.isChecked=true
                    else if(viewModel.getAddedSortId().value==3)
                        popMax.isChecked=true
                }
            })

            diffHardest.setOnClickListener {
                viewModel.setPopAddedSort(null)
                viewModel.setDiffAddedSort(SortMethods.difMaxtoMin)
                viewModel.setAddedSortId(0)
            }
            diffEasiest.setOnClickListener {
                viewModel.setPopAddedSort(null)
                viewModel.setDiffAddedSort(SortMethods.difMintoMax)
                viewModel.setAddedSortId(1)
            }
            popMax.setOnClickListener {
                viewModel.setDiffAddedSort(null)
                viewModel.setPopAddedSort(SortMethods.popMaxtoMin)
                viewModel.setAddedSortId(3)
            }
            popMin.setOnClickListener {
                viewModel.setDiffAddedSort(null)
                viewModel.setPopAddedSort(SortMethods.popMintoMax)
                viewModel.setAddedSortId(2)
            }

            cancelButton.setOnClickListener {
                viewModel.resetAddedSort()
                addedRecipesViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(sortMethod.checkedRadioButtonId==-1) {
                    Toast.makeText(requireContext(), "Choose a sort method first.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    addedRecipesViewModel.setSort(false)
                    Hawk.delete(Constants.SORT_DIRECTION)
                }
            }
        }
        else if(direction==Constants.PURRFECTED_TO_SORT)
        {
            viewModel.getPurrfectedSortId().observe(viewLifecycleOwner,{
                if(viewModel.getPurrfectedSortId().value!=-1 && sortMethod.checkedRadioButtonId==-1) {
                    if(viewModel.getPurrfectedSortId().value==0)
                        diffHardest.isChecked=true
                    else if(viewModel.getPurrfectedSortId().value==1)
                        diffEasiest.isChecked=true
                    else if(viewModel.getPurrfectedSortId().value==2)
                        popMin.isChecked=true
                    else if(viewModel.getPurrfectedSortId().value==3)
                        popMax.isChecked=true
                }
            })

            diffHardest.setOnClickListener {
                viewModel.setPopPurrfectedSort(null)
                viewModel.setDiffPurrfectedSort(SortMethods.difMaxtoMin)
                viewModel.setPurrfectedSortId(0)
            }
            diffEasiest.setOnClickListener {
                viewModel.setPopPurrfectedSort(null)
                viewModel.setDiffPurrfectedSort(SortMethods.difMintoMax)
                viewModel.setPurrfectedSortId(1)
            }
            popMax.setOnClickListener {
                viewModel.setDiffPurrfectedSort(null)
                viewModel.setPopPurrfectedSort(SortMethods.popMaxtoMin)
                viewModel.setPurrfectedSortId(3)
            }
            popMin.setOnClickListener {
                viewModel.setDiffPurrfectedSort(null)
                viewModel.setPopPurrfectedSort(SortMethods.popMintoMax)
                viewModel.setPurrfectedSortId(2)
            }

            cancelButton.setOnClickListener {
                viewModel.resetPurrfectedSort()
                purrfectedRecipesViewModel.setSort(false)
                Hawk.delete(Constants.SORT_DIRECTION)
            }

            enterButton.setOnClickListener {
                if(sortMethod.checkedRadioButtonId==-1) {
                    Toast.makeText(requireContext(), "Choose a sort method first.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    purrfectedRecipesViewModel.setSort(false)
                    Hawk.delete(Constants.SORT_DIRECTION)
                }
            }
        }


    }

}