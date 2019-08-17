package io.github.vnicius.picpay.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.adapters.ContactsAdapter
import io.github.vnicius.picpay.adapters.ItemClick
import io.github.vnicius.picpay.data.model.Contact
import kotlinx.android.synthetic.main.fragment_contacts_list.*

/**
 * [Fragment] to show a [List] of [Contact]
 */
class ContactsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts_list, container, false)
        val rvContacts = view.findViewById<RecyclerView>(R.id.rv_contacts)
        val contacts = arguments?.getSerializable(ARG_DATA) as List<Contact>?

        rvContacts.layoutManager = LinearLayoutManager(this.context)
        rvContacts.adapter = contacts?.let { ContactsAdapter(it, object: ItemClick<Contact> {
            override fun onClick(view: View, item: Contact) {
                Toast.makeText(view.context, item.name, Toast.LENGTH_SHORT).show()
            }
        }) }

        return view
    }

    companion object {
        @JvmStatic
        val ARG_DATA = "contacts"
        fun newInstance() = ContactsListFragment()
    }
}
