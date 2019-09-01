package io.github.vnicius.picpay.ui.contacts.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Card
import io.github.vnicius.picpay.ui.contacts.adapters.ContactsAdapter
import io.github.vnicius.picpay.ui.common.adapters.ItemClick
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.data.repository.cards.CardsRepository
import io.github.vnicius.picpay.data.repository.cards.CardsRepositoryLocal
import io.github.vnicius.picpay.ui.cardpriming.CardPrimingActivity
import io.github.vnicius.picpay.ui.cardregister.CardRegisterActivity
import io.github.vnicius.picpay.ui.contacts.adapters.CardsAdapter
import io.github.vnicius.picpay.ui.payment.PaymentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * [Fragment] to show a [List] of [User]
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
        val contacts = arguments?.getSerializable(ARG_DATA) as List<User>?

        rvContacts.layoutManager = LinearLayoutManager(this.context)
        rvContacts.adapter = contacts?.let {
            ContactsAdapter(it, object : ItemClick<User> {
                override fun onClick(view: View, item: User) {
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

    private fun checkCards(contact: User) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            val cards = mCardsRepository.getAllCards().await()

            if(cards.isEmpty()) {
                showCardPriming(contact)
            } else {
                showCards(cards, contact)
            }
        }
    }

    private fun showCards(cards: List<Card>, contact: User) {
        val bottomSheet = BottomSheetDialog(context!!)
        val sheetView = layoutInflater.inflate(R.layout.cards_sheet, null)
        val rvCards = sheetView.findViewById<RecyclerView>(R.id.rv_cards)
        val btnRegister = sheetView.findViewById<Button>(R.id.btn_register_card)

        rvCards.layoutManager = LinearLayoutManager(context)
        rvCards.adapter = CardsAdapter(cards,
            object : ItemClick<Card>{
                override fun onClick(view: View, item: Card) {
                    val intent = Intent(view.context, PaymentActivity::class.java)
                    intent.putExtra(PaymentActivity.ARG_DATA_CONTACT, contact as Serializable)
                    intent.putExtra(PaymentActivity.ARG_DATA_CARD, item as Serializable)

                    bottomSheet.cancel()
                    startActivity(intent)
                }
            })

        btnRegister.setOnClickListener {
            val intent = Intent(it.context, CardRegisterActivity::class.java)
            intent.putExtra(CardRegisterActivity.ARG_DATA, contact as Serializable)

            bottomSheet.cancel()
            startActivity(intent)
        }

        bottomSheet.setContentView(sheetView)
        bottomSheet.setOnShowListener {
            val bs = bottomSheet.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bs).state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheet.show()
    }

    private fun showCardPriming(contact: User) {
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
