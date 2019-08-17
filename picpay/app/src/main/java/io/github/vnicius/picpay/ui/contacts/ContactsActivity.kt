package io.github.vnicius.picpay.ui.contacts

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.vnicius.picpay.R
import io.github.vnicius.picpay.data.model.Contact
import io.github.vnicius.picpay.ui.common.adapters.ItemClick
import io.github.vnicius.picpay.ui.common.fragments.NoConnectionFragment
import io.github.vnicius.picpay.ui.common.fragments.NoResultFragment
import io.github.vnicius.picpay.ui.common.fragments.SpinnerFragment
import io.github.vnicius.picpay.ui.contacts.fragments.ContactsListFragment
import io.github.vnicius.picpay.ui.contacts.fragments.NoContactsFragment
import io.github.vnicius.picpay.utils.NetworkUtils
import kotlinx.android.synthetic.main.search_widget.*
import java.io.Serializable

class ContactsActivity : AppCompatActivity(), ContactsContract.View {

    private lateinit var searchIcon: ImageView
    private val mPresenter: ContactsContract.Presenter = ContactsPresenter(this)
    private val mFragmentManager = supportFragmentManager
    private lateinit var contactsList: List<Contact>

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
    }

    private fun changeFragment(fragment: Fragment){
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

    override fun showContacts(contacts: List<Contact>) {
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
