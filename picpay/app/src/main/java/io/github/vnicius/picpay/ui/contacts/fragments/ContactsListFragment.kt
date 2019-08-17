package io.github.vnicius.picpay.ui.contacts.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.ui.contacts.adapters.ContactsAdapter
import io.github.vnicius.picpay.ui.common.adapters.ItemClick
import io.github.vnicius.picpay.data.model.Contact
import io.github.vnicius.picpay.data.repository.cards.CardsRepository
import io.github.vnicius.picpay.data.repository.cards.CardsRepositoryLocal
import io.github.vnicius.picpay.ui.cardpriming.CardPrimingActivity
import io.github.vnicius.picpay.ui.contacts.ContactsContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * [Fragment] to show a [List] of [Contact]
 */
class ContactsListFragment : Fragment() {

    private lateinit var mCardsRepository: CardsRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mCardsRepository = CardsRepositoryLocal(context!!)

        val view = inflater.inflate(R.layout.fragment_contacts_list, container, false)
        val rvContacts = view.findViewById<RecyclerView>(R.id.rv_contacts)
        val contacts = arguments?.getSerializable(ARG_DATA) as List<Contact>?

        rvContacts.layoutManager = LinearLayoutManager(this.context)
        rvContacts.adapter = contacts?.let {
            ContactsAdapter(it, object : ItemClick<Contact> {
                override fun onClick(view: View, item: Contact) {
                    checkCards(item)
                }
            })
        }

        return view
    }

    override fun onDestroy() {
        if(mCardsRepository is CardsRepositoryLocal) {
            (mCardsRepository as CardsRepositoryLocal).finish()
        }
        super.onDestroy()
    }

    private fun checkCards(contact: Contact) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            val cards = mCardsRepository.getAllCards().await()

            if(cards.isEmpty()) {
                showCardPriming(contact)
            } else {
                showCards(contact)
            }
        }
    }

    private fun showCards(contact: Contact) {
        val bottomSheet = BottomSheetDialog(context!!)
        val sheetView = layoutInflater.inflate(R.layout.cards_sheet, null)

        bottomSheet.setContentView(sheetView)
        bottomSheet.show()
    }

    private fun showCardPriming(contact: Contact) {
        val intent = Intent(context, CardPrimingActivity::class.java)
        intent.putExtra(CardPrimingActivity.ARG_DATA, contact as Serializable)

        startActivity(intent)
    }

    companion object {
        @JvmStatic
        val ARG_DATA = "contacts"
        fun newInstance() = ContactsListFragment()
    }
}
