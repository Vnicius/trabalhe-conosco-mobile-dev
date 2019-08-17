package io.github.vnicius.picpay.ui.contacts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.vnicius.picpay.R

/**
 * [Fragment] to show a message when not have contacts
 */
class NoContactsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_contacts, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoContactsFragment()
    }


}
