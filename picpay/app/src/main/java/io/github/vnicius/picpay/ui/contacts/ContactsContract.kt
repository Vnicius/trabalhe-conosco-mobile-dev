package io.github.vnicius.picpay.ui.contacts

import io.github.vnicius.picpay.data.model.User

/**
 * Contacts UI contract
 */
interface ContactsContract {

    /**
     * View interface
     */
    interface View {
        /**
         * Show the contacts
         * @param contacts a [List] of [User]
         */
        fun showContacts(contacts: List<User>)

        /**
         * Show "no result" message to a query
         * @param query searched
         */
        fun showNoResult(query: String)

        /**
         * Show "no contacts" message
         */
        fun showNoContacts()

        /**
         * Show load screen
         */
        fun showLoad()

        /**
         * Show error message
         * @param message
         */
        fun showError(message: String)

        /**
         * Show try again screen
         */
        fun showTryAgain()

    }

    /**
     * Presenter interface
     */
    interface Presenter {

        /**
         * Get the contacts from anywhere
         */
        fun getContacts()

        /**
         * Search the contacts by a query
         * @param contacts
         * @param query
         */
        fun searchContact(contacts: List<User>, query: String)
    }
}