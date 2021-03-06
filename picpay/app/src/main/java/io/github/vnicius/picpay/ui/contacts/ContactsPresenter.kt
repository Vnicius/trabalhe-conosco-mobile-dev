package io.github.vnicius.picpay.ui.contacts

import io.github.vnicius.picpay.data.model.User
import io.github.vnicius.picpay.data.repository.contacts.ContactsRepository
import io.github.vnicius.picpay.data.repository.contacts.ContactsRepositoryRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ContactsPresenter(val view: ContactsContract.View): ContactsContract.Presenter {

    private val contactsRepository: ContactsRepository = ContactsRepositoryRemote()

    override fun getContacts() {
        val scope = CoroutineScope(Dispatchers.Main)

        view.showLoad()

        scope.launch {
            var contacts: List<User>?

            try {
                coroutineScope {
                    contacts = contactsRepository.getContacts().await()

                    if(contacts == null || contacts?.isEmpty()!!) {
                        view.showNoContacts()
                    } else {
                        view.showContacts(contacts!!)
                    }
                }
            } catch (e: UnknownHostException) {
                view.showTryAgain()
            } catch (e: Exception) {
                view.showError("Ocorreu algum erro")
            }
        }
    }

    override fun searchContact(contacts: List<User>, query: String) {
        val filteredContacts = contacts.filter {
            query.toLowerCase() in it.name.toLowerCase() || query.toLowerCase() in it.username.toLowerCase()
        }

        if(filteredContacts.isEmpty()) {
            view.showNoResult(query)
        } else {
            view.showContacts(filteredContacts)
        }
    }
}