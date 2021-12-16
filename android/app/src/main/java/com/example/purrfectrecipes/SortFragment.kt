package com.example.purrfectrecipes

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        super.onViewCreated(view, savedInstanceState)

        val cancelButton=view.findViewById<ImageView>(R.id.cancelSort)
        val enterButton=view.findViewById<LinearLayout>(R.id.enterSortButton)

        val diffHardest=view.findViewById<RadioButton>(R.id.diffHardest)
        val diffEasiest=view.findViewById<RadioButton>(R.id.diffEasiest)
        val popMax=view.findViewById<RadioButton>(R.id.popMost)
        val popMin=view.findViewById<RadioButton>(R.id.popLeast)

        val direction= Hawk.get<String>(Constants.SORT_DIRECTION)
        if(direction==Constants.MAIN_TO_SORT)
        {
            diffHardest.setOnClickListener {
                homeViewModel.setDiffSort(SortMethods.difMaxtoMin)
            }
            diffEasiest.setOnClickListener {
                homeViewModel.setDiffSort(SortMethods.difMintoMax)
            }
            popMax.setOnClickListener {
                homeViewModel.setPopSort(SortMethods.popMaxtoMin)
            }
            popMin.setOnClickListener {
                homeViewModel.setPopSort(SortMethods.popMintoMax)
            }
            cancelButton.setOnClickListener {
                homeViewModel.setPopSort(null)
                homeViewModel.setDiffSort(null)
                homeViewModel.setSort(false)
            }
        }

        enterButton.setOnClickListener {
            homeViewModel.setSort(false)
        }

    }

}