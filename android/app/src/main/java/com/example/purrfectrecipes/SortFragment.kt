package com.example.purrfectrecipes

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.purrfectrecipes.Customer.CustomerActivity
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel
import com.example.purrfectrecipes.Guest.GuestActivity
import com.example.purrfectrecipes.User.User
import com.orhanobut.hawk.Hawk

class SortFragment: Fragment(R.layout.fragment_sort) {

    private val homeViewModel:RecipesHomeViewModel by activityViewModels()
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

        val direction= Hawk.get<String>(Constants.SORT_DIRECTION)
        if(direction==Constants.MAIN_TO_SORT)
        {
            diffHardest.setOnClickListener {
                homeViewModel.setDiffSort(SortMethods.difMaxtoMin)
                viewModel.setHomeSortId(0)
            }
            diffEasiest.setOnClickListener {
                homeViewModel.setDiffSort(SortMethods.difMintoMax)
                viewModel.setHomeSortId(1)
            }
            popMax.setOnClickListener {
                homeViewModel.setPopSort(SortMethods.popMaxtoMin)
                viewModel.setHomeSortId(3)
            }
            popMin.setOnClickListener {
                homeViewModel.setPopSort(SortMethods.popMintoMax)
                viewModel.setHomeSortId(2)
            }
        }

        cancelButton.setOnClickListener {
            homeViewModel.resetSort()
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

}