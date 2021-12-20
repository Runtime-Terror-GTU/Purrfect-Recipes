package com.example.purrfectrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val homeViewModel: RecipesHomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton=view.findViewById<ImageView>(R.id.cancelFilter)
        val enterButton=view.findViewById<LinearLayout>(R.id.enterFilterButton)

        cancelButton.setOnClickListener {

            homeViewModel.setFilter(false)
        }

        enterButton.setOnClickListener {
                homeViewModel.setFilter(false)
        }
    }
}