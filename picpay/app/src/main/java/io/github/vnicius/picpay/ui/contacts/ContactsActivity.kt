package io.github.vnicius.picpay.ui.contacts

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Transaction
import io.github.vnicius.picpay.data.model.TransactionResponse
import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.ui.common.fragments.NoConnectionFragment
import io.github.vnicius.picpay.ui.common.fragments.NoResultFragment
import io.github.vnicius.picpay.ui.common.fragments.SpinnerFragment
import io.github.vnicius.picpay.ui.contacts.fragments.ContactsListFragment
import io.github.vnicius.picpay.ui.contacts.fragments.NoContactsFragment
import io.github.vnicius.picpay.utils.NetworkUtils
import io.github.vnicius.picpay.utils.PaymentUtils
import kotlinx.android.synthetic.main.search_widget.*
import java.io.Serializable

class ContactsActivity : AppCompatActivity(), ContactsContract.View {

    private lateinit var searchIcon: ImageView
    private val mPresenter: ContactsContract.Presenter = ContactsPresenter(this)
    private val mFragmentManager = supportFragmentManager
    private lateinit var contactsList: List<User>

    companion object {
        const val ARG_TRASACTION = "transaction"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        searchIcon = sv_contacts.findViewById(androidx.appcompat.R.id.search_mag_icon)
        disabledSearch()

        if(NetworkUtils.hasConnection(baseContext)) {
            mPresenter.getContacts()
        } else {
            showTryAgain()
        }

        sv_contacts.setOnQueryTextFocusChangeListener { view, b ->
            if(b) {
                enabledSearch()
            } else {
                disabledSearch()
            }
        }

        sv_contacts.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null && ::contactsList.isInitialized) {
                    mPresenter.searchContact(contactsList, query)
                } else {
                    showContacts(contactsList)
                }
                return true
            }
        })

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val transactionResponse = intent.getSerializableExtra(ARG_TRASACTION) as TransactionResponse?

        if (transactionResponse != null) {
            if(transactionResponse.transaction.success) {
                showTransactionReceipt(transactionResponse)
            } else {
                showError("Ocorreu algum erro na transação")
            }
        }
    }

    private fun showTransactionReceipt(transactionResponse: TransactionResponse) {
        val transaction = transactionResponse.transaction
        val bottomSheetDialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.transaction_sheet, null)
        val value = PaymentUtils.moneyParser(transaction.value)

        Picasso.get().load(transaction.destination_user.img)
            .into(sheetView.findViewById<ImageView>(R.id.iv_transaction_user_avatar))

        sheetView.findViewById<TextView>(R.id.tv_transaction_username).text = transaction.destination_user.username
        sheetView.findViewById<TextView>(R.id.tv_transaction_timestamp).text = PaymentUtils.timeParser(transaction.timestamp)
        sheetView.findViewById<TextView>(R.id.tv_transaction_id).text = "Transação: ${transaction.id.toString()}"
        sheetView.findViewById<TextView>(R.id.tv_transaction_card_name).text = "Card ${transactionResponse.cardNumber.subSequence(12, 16)}"
        sheetView.findViewById<TextView>(R.id.tv_transaction_card_value).text = "R$ $value"
        sheetView.findViewById<TextView>(R.id.tv_transaction_value).text = "R$ $value"

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setOnShowListener {
            val bs = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bs).state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheetDialog.show()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun changeFragment(fragment: Fragment){
        mFragmentManager.popBackStack()
        val transaction = mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_contacts, fragment)
            addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }

    private fun disabledSearch() {
        val layoutParams = sv_contacts.layoutParams

        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        sv_contacts.layoutParams = layoutParams
        search_bar_bg.background = ContextCompat.getDrawable(baseContext, R.drawable.searchbar_disable)
        searchIcon.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorSecondaryIcon, null), PorterDuff.Mode.SRC_ATOP)
    }

    private fun enabledSearch() {
        val layoutParams = sv_contacts.layoutParams

        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        sv_contacts.layoutParams = layoutParams
        search_bar_bg.background = ContextCompat.getDrawable(baseContext, R.drawable.searchbar_enable)
        searchIcon.setColorFilter(ResourcesCompat.getColor(resources, R.color.white, null), PorterDuff.Mode.SRC_ATOP)
    }

    override fun showContacts(contacts: List<User>) {
        if(!::contactsList.isInitialized) {
            contactsList = contacts
        }

        val fragment = ContactsListFragment.newInstance()
        val args = Bundle()
        args.putSerializable(ContactsListFragment.ARG_DATA, contacts as Serializable)
        fragment.arguments = args

        changeFragment(fragment)
    }

    override fun showNoResult(query: String) {
        changeFragment(NoResultFragment.newInstance(query))
    }

    override fun showNoContacts() {
        changeFragment(NoContactsFragment.newInstance())
    }

    override fun showLoad() {
        changeFragment(SpinnerFragment.newInstance())
    }

    override fun showError(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showTryAgain() {
        changeFragment(NoConnectionFragment.newInstance {
            mPresenter.getContacts()
        })
    }
}
