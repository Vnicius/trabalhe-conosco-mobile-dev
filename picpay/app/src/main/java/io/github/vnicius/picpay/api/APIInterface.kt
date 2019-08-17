package io.github.vnicius.picpay.api

import io.github.vnicius.picpay.data.model.Contact
import kotlinx.coroutines.Deferred

/**
 * API connection interface
 */
interface APIInterface {
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