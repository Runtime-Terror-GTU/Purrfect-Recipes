package com.example.purrfectrecipes.Customer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.purrfectrecipes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.util.*

class SuggestSettingsChildfragment: Fragment(R.layout.childfragment_settings_suggest)
{
    private val viewModel: SettingsSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getSuggestView().observe(viewLifecycleOwner, {
            if(viewModel.getSuggestView().value!=null)
                super.onViewCreated(viewModel.getSuggestView().value!!, savedInstanceState)
            else
            {
                viewModel.setSuggestView(view)
                super.onViewCreated(view, savedInstanceState)
            }
        })

        val enterSuggestIngredientButton=view.findViewById<TextView>(R.id.enterSuggestButton)
        val suggestedIngredient = view.findViewById<EditText>(R.id.inputSuggestedIngredient)
        enterSuggestIngredientButton.setOnClickListener{

            if(suggestedIngredient.text.isEmpty()){
                Toast.makeText(requireActivity(), "Your suggestion has not been received.", Toast.LENGTH_SHORT).show()
            }else{
                val ingredients: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Suggested Ingredients")
                ingredients.addListenerForSingleValueEvent(object :
                    ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            Toast.makeText(requireActivity(), "Your suggestion has been received.", Toast.LENGTH_SHORT).show()

                            ingredients.child(suggestedIngredient.text.toString().lowercase()).setValue(true)
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(requireActivity(), "Your suggestion has not been received."+error, Toast.LENGTH_SHORT).show()
                        }
                })
            }

        }

    }

}