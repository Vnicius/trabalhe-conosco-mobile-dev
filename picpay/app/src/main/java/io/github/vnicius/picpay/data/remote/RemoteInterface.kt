package io.github.vnicius.picpay.data.remote

import io.github.vnicius.picpay.data.model.Contact
import kotlinx.coroutines.Deferred

/**
 * API connection interface
 */
interface RemoteInterface {
    /**
     * Get contacts
     * @return
     */
    fun getContacts(): Deferred<List<Contact>?>

    /**
     * Make the transaction
     */
    fun makeTransaction()
}