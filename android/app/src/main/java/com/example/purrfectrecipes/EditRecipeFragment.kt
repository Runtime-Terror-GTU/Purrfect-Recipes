package com.example.purrfectrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.purrfectrecipes.Customer.AddedrecipesProfileViewModel

class EditRecipeFragment : Fragment(R.layout.fragment_edit_recipe) {

    private val addedRecipesViewModel:AddedrecipesProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelEditButton=view.findViewById<ImageView>(R.id.cancelEdit)
        cancelEditButton.setOnClickListener {
            addedRecipesViewModel.setEditRecipe(false)
        }
    }
}