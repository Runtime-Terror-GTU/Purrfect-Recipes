package com.example.purrfectrecipes.Guest

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.purrfectrecipes.Customer.ContactSettingsChildfragment
import com.example.purrfectrecipes.R

class ContactGuestFragment: Fragment(R.layout.fragment_guest_contact)
{
    private val contactChildFragment=ContactSettingsChildfragment();
    override fun onCreate(savedInstanceState: Bundle?) {

        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.childfragment_contact, contactChildFragment);
        fragmentTransaction.commit()

        super.onCreate(savedInstanceState)
    }

}