package com.example.purrfectrecipes

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.viewModels
import com.example.purrfectrecipes.Customer.CustomerActivity
import com.example.purrfectrecipes.Customer.RecipesHomeViewModel
import com.example.purrfectrecipes.Guest.GuestActivity
import com.example.purrfectrecipes.User.User
import com.orhanobut.hawk.Hawk

class SortActivity : AppCompatActivity() {

    private val homeViewModel:RecipesHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)

        val cancelButton=findViewById<ImageView>(R.id.cancelSort)
        val enterButton=findViewById<LinearLayout>(R.id.enterSortButton)

        val diffHardest=findViewById<RadioButton>(R.id.diffHardest)
        val diffEasiest=findViewById<RadioButton>(R.id.diffEasiest)
        val popMax=findViewById<RadioButton>(R.id.popMost)
        val popMin=findViewById<RadioButton>(R.id.popLeast)

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
                finish()
            }
        }

        enterButton.setOnClickListener {
            finish()
        }

    }
}